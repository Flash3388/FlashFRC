package edu.flash3388.flashlib.frc.robot;

import edu.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.modes.RobotMode;
import edu.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.flash3388.flashlib.robot.scheduling.SchedulerRunMode;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class IterativeFrcRobotBase extends FrcRobotBase {

    private final Scheduler mScheduler;

    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    protected IterativeFrcRobotBase(Scheduler scheduler) {
        super(scheduler);
        mScheduler = scheduler;

        mCurrentMode = null;
        mLastMode = null;
        mWasModeInitialized = false;

        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Iterative);
    }

    protected IterativeFrcRobotBase() {
        this(RobotFactory.newDefaultScheduler());
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

    private void initMode(FrcRobotMode mode) {
        LiveWindow.setEnabled(mode.isLiveWindowEnabled());

        if (mode.equals(FrcRobotMode.DISABLED)) {
            mScheduler.setRunMode(SchedulerRunMode.TASKS_ONLY);
            mScheduler.removeAllActions();

            disabledInit();
        } else {
            mScheduler.setRunMode(SchedulerRunMode.ALL);
            mScheduler.removeAllActions();

            modeInit(mode);
        }
    }

    private void periodicMode(FrcRobotMode mode) {
        mode.reportModeHal();

        if (mode.equals(RobotMode.DISABLED)) {
            disabledPeriodic();
        } else {
            mScheduler.run();
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
            }
            case FrcRobotMode.AUTONOMOUS_KEY: {
                autonomousInit();
            }
            case FrcRobotMode.TEST_KEY: {
                testInit();
            }
        }
    }

    private void modePeriodic(FrcRobotMode mode) {
        switch (mode.getKey()) {
            case FrcRobotMode.OPERATOR_CONTROL_KEY: {
                teleopPeriodic();
            }
            case FrcRobotMode.AUTONOMOUS_KEY: {
                autonomousPeriodic();
            }
            case FrcRobotMode.TEST_KEY: {
                testPeriodic();
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
