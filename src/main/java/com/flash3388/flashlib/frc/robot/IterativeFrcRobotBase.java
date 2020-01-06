package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class IterativeFrcRobotBase extends FrcRobotBase {

    protected static final Time DEFAULT_LOOP_PERIOD = Time.milliseconds(20);

    private final Scheduler mScheduler;

    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    protected IterativeFrcRobotBase(RobotConfiguration configuration, Scheduler scheduler, Time expectedRunPeriod) {
        super(configuration, scheduler);
        mScheduler = scheduler;
        init();
    }

    protected IterativeFrcRobotBase(RobotConfiguration configuration) {
        super(configuration);
        mScheduler = getScheduler();
        init();
    }

    protected IterativeFrcRobotBase() {
        this(RobotConfiguration.defaultConfiguration());
    }

    protected final void loop() {
        mCurrentMode = FrcRobotMode.cast(getMode());

        if (!mCurrentMode.equals(mLastMode)) {
            mLastMode = mCurrentMode;
            mWasModeInitialized = false;
        }

        if (!mWasModeInitialized) {
            initMode(mCurrentMode);
            mWasModeInitialized = true;
        }

        periodicMode(mCurrentMode);
    }

    private void init() {
        mCurrentMode = null;
        mLastMode = null;
        mWasModeInitialized = false;
    }

    private void initMode(FrcRobotMode mode) {
        LiveWindow.setEnabled(mode.isLiveWindowEnabled());
        mode.configureShuffleboardWidgets();

        if (mode.isDisabled()) {
            disabledInit();
        } else {
            modeInit(mode);
        }
    }

    private void periodicMode(FrcRobotMode mode) {
        mode.reportModeHal();

        mScheduler.run(mode);

        if (mode.isDisabled()) {
            disabledPeriodic();
        } else {
            modePeriodic(mode);
        }

        robotPeriodic();

        SmartDashboard.updateValues();
        LiveWindow.updateValues();
        Shuffleboard.update();
    }

    private void modeInit(FrcRobotMode mode) {
        switch (mode) {
            case OPERATOR_CONTROL: {
                teleopInit();
                break;
            }
            case AUTONOMOUS: {
                autonomousInit();
                break;
            }
            case TEST: {
                testInit();
                break;
            }
        }
    }

    private void modePeriodic(FrcRobotMode mode) {
        switch (mode) {
            case OPERATOR_CONTROL: {
                teleopPeriodic();
                break;
            }
            case AUTONOMOUS: {
                autonomousPeriodic();
                break;
            }
            case TEST: {
                testPeriodic();
                break;
            }
        }
    }

    //--------------------------------------------------------------------
    //----------------------Implementable---------------------------------
    //--------------------------------------------------------------------

    protected abstract void robotInit();

    protected abstract void disabledInit();
    protected abstract void disabledPeriodic();

    protected abstract void teleopInit();
    protected abstract void teleopPeriodic();

    protected abstract void autonomousInit();
    protected abstract void autonomousPeriodic();

    protected abstract void testInit();
    protected abstract void testPeriodic();

    protected abstract void robotPeriodic();
}
