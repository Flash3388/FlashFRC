package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.app.StartupException;
import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.RunningRobot;
import com.flash3388.flashlib.robot.base.iterative.RobotLooper;
import com.flash3388.flashlib.scheduling.actions.ActionFlag;
import com.flash3388.flashlib.util.resources.ResourceHolder;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopingRobotBase extends RobotBase {

    private final IterativeFrcRobot.Initializer mRobotInitializer;
    private final ResourceHolder mResourceHolder;
    private final FrcRobotControl mRobotControl;
    private final RobotLooper mRobotLooper;

    private IterativeFrcRobot mRobot;
    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    public LoopingRobotBase(IterativeFrcRobot.Initializer robotInitializer, ResourceHolder resourceHolder, FrcRobotControl robotControl, RobotLooper robotLooper) {
        mRobotInitializer = robotInitializer;
        mResourceHolder = resourceHolder;
        mRobotControl = robotControl;
        mRobotLooper = robotLooper;

        mRobot = null;
        mCurrentMode = null;
        mLastMode = null;
        mWasModeInitialized = false;
    }

    public LoopingRobotBase(IterativeFrcRobot.Initializer robotInitializer, FrcRobotControl robotControl, ResourceHolder resourceHolder) {
        this(robotInitializer, resourceHolder, robotControl, new NotifierRobotLooper());
    }

    @Override
    public void startCompetition() {
        try {
            mRobot = mRobotInitializer.init(mRobotControl);
        } catch (StartupException e) {
            throw new RuntimeException(e);
        }

        DriverStationJNI.observeUserProgramStarting();
        mRobotLooper.startLooping(mRobotControl.getClock(), this::loop);
    }

    @Override
    public void endCompetition() {
        mRobot.robotStop();
        mRobotLooper.stop();

        try {
            mResourceHolder.freeAll();
        } catch (Throwable throwable) {
            mRobotControl.getLogger().warn("Error freeing resources", throwable);
        }
    }

    private void loop() {
        DriverStation.refreshData();

        mCurrentMode = mRobotControl.getMode(FrcRobotMode.class);

        if (!mCurrentMode.equals(mLastMode)) {
            exitMode(mCurrentMode);

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
        mRobotControl.getLogger().debug("Initializing mode {}", mode);

        LiveWindow.setEnabled(mode.isLiveWindowEnabled());
        mode.configureShuffleboardWidgets();

        mRobotControl.getScheduler().cancelActionsIfWithoutFlag(ActionFlag.RUN_ON_DISABLED);

        if (mode.isDisabled()) {
            mRobot.disabledInit();
        } else {
            modeInit(mode);
        }
    }

    private void periodicMode(FrcRobotMode mode) {
        mRobotControl.getLogger().debug("Periodic mode {}", mode);

        mode.reportModeHal();

        mRobotControl.getMainThread().executePendingTasks();
        mRobotControl.getServiceRegistry().startAll();
        mRobotControl.getScheduler().run(mode);

        if (mode.isDisabled()) {
            mRobot.disabledPeriodic();
        } else {
            modePeriodic(mode);
        }

        mRobot.robotPeriodic();

        SmartDashboard.updateValues();
        LiveWindow.updateValues();
        Shuffleboard.update();

        if (isSimulation()) {
            HAL.simPeriodicBefore();
            mRobot.simulationPeriodic();
            HAL.simPeriodicAfter();
        }
    }

    private void exitMode(FrcRobotMode mode) {
        mRobotControl.getLogger().debug("Exiting mode {}", mode);

        if (mode.isDisabled()) {
            mRobot.disabledExit();
        } else {
            modeExit(mode);
        }
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

    private void modeExit(FrcRobotMode mode) {
        switch (mode) {
            case OPERATOR_CONTROL: {
                mRobot.teleopExit();
                break;
            }
            case AUTONOMOUS: {
                mRobot.autonomousExit();
                break;
            }
            case TEST: {
                mRobot.testExit();
                break;
            }
        }
    }
}
