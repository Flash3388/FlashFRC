package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.modes.RobotMode;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class IterativeFrcRobotBase extends FrcRobotBase {

    private final Scheduler mScheduler;

    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    protected IterativeFrcRobotBase(RobotConfiguration configuration, Scheduler scheduler) {
        super(configuration, scheduler);
        mScheduler = scheduler;
        init();
    }

    protected IterativeFrcRobotBase(Scheduler scheduler) {
        super(RobotConfiguration.defaultConfiguration(), scheduler);
        mScheduler = scheduler;
        init();
    }

    protected IterativeFrcRobotBase(RobotConfiguration configuration) {
        super(configuration);
        mScheduler = getScheduler();
        init();
    }

    protected IterativeFrcRobotBase() {
        super();
        mScheduler = getScheduler();
        init();
    }

    protected void loop() {
        mCurrentMode = FrcRobotMode.forKey(getMode().getKey());

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

        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Iterative);
    }

    private void initMode(FrcRobotMode mode) {
        LiveWindow.setEnabled(mode.isLiveWindowEnabled());

        if (mode.equals(FrcRobotMode.DISABLED)) {
            disabledInit();
        } else {
            modeInit(mode);
        }
    }

    private void periodicMode(FrcRobotMode mode) {
        mode.reportModeHal();

        mScheduler.run(mode);

        if (mode.equals(RobotMode.DISABLED)) {
            disabledPeriodic();
        } else {
            modePeriodic(mode);
        }

        robotPeriodic();

        SmartDashboard.updateValues();
        LiveWindow.updateValues();
    }

    private void modeInit(FrcRobotMode mode) {
        switch (mode.getKey()) {
            case FrcRobotMode.OPERATOR_CONTROL_KEY: {
                teleopInit();
                break;
            }
            case FrcRobotMode.AUTONOMOUS_KEY: {
                autonomousInit();
                break;
            }
            case FrcRobotMode.TEST_KEY: {
                testInit();
                break;
            }
        }
    }

    private void modePeriodic(FrcRobotMode mode) {
        switch (mode.getKey()) {
            case FrcRobotMode.OPERATOR_CONTROL_KEY: {
                teleopPeriodic();
                break;
            }
            case FrcRobotMode.AUTONOMOUS_KEY: {
                autonomousPeriodic();
                break;
            }
            case FrcRobotMode.TEST_KEY: {
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
