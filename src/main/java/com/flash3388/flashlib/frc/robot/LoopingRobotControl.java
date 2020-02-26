package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.IterativeFrcRobot;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.RobotInitializationException;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class LoopingRobotControl extends FrcRobotControlBase {

    protected static final Time DEFAULT_LOOP_PERIOD = Time.milliseconds(20);

    private final Scheduler mScheduler;
    private final IterativeFrcRobot.Initializer mRobotInitializer;

    private IterativeFrcRobot mRobot;
    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    protected LoopingRobotControl(RobotConfiguration configuration, Scheduler scheduler, Time expectedRunPeriod, IterativeFrcRobot.Initializer robotInitializer) {
        super(configuration, scheduler);
        mScheduler = scheduler;
        mRobotInitializer = robotInitializer;
        init();
    }

    protected LoopingRobotControl(RobotConfiguration configuration, IterativeFrcRobot.Initializer robotInitializer) {
        super(configuration);
        mRobotInitializer = robotInitializer;
        mScheduler = getScheduler();
        init();
    }

    protected LoopingRobotControl(IterativeFrcRobot.Initializer robotInitializer) {
        this(RobotConfiguration.defaultConfiguration(), robotInitializer);
    }

    @Override
    public void startCompetition() {
        try {
            mRobot = mRobotInitializer.init(this);
        } catch (RobotInitializationException e) {
            throw new RuntimeException(e);
        }

        HAL.observeUserProgramStarting();
        robotLoop();
    }

    @Override
    public void endCompetition() {
        mRobot.robotStop();
        robotStop();
    }

    protected final void loop() {
        mCurrentMode = getMode(FrcRobotMode.class);

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
            mRobot.disabledInit();
        } else {
            modeInit(mode);
        }
    }

    private void periodicMode(FrcRobotMode mode) {
        mode.reportModeHal();

        mScheduler.run(mode);

        if (mode.isDisabled()) {
            mRobot.disabledPeriodic();
        } else {
            modePeriodic(mode);
        }

        mRobot.robotPeriodic();

        SmartDashboard.updateValues();
        LiveWindow.updateValues();
        Shuffleboard.update();
    }

    private void modeInit(FrcRobotMode mode) {
        switch (mode) {
            case OPERATOR_CONTROL: {
                mRobot.teleopInit();
                break;
            }
            case AUTONOMOUS: {
                mRobot.autonomousInit();
                break;
            }
            case TEST: {
                mRobot.testInit();
                break;
            }
        }
    }

    private void modePeriodic(FrcRobotMode mode) {
        switch (mode) {
            case OPERATOR_CONTROL: {
                mRobot.teleopPeriodic();
                break;
            }
            case AUTONOMOUS: {
                mRobot.autonomousPeriodic();
                break;
            }
            case TEST: {
                mRobot.testPeriodic();
                break;
            }
        }
    }

    protected abstract void robotLoop();
    protected abstract void robotStop();
}
