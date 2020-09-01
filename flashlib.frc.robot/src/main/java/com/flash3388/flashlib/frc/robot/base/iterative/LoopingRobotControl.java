package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.RobotInitializationException;
import com.flash3388.flashlib.robot.base.iterative.RobotLooper;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopingRobotControl extends RobotBase {

    private final IterativeFrcRobot.Initializer mRobotInitializer;
    private final RobotControl mRobotControl;
    private final RobotLooper mRobotLooper;

    private IterativeFrcRobot mRobot;
    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    public LoopingRobotControl(IterativeFrcRobot.Initializer robotInitializer, RobotControl robotControl, RobotLooper robotLooper) {
        mRobotInitializer = robotInitializer;
        mRobotControl = robotControl;
        mRobotLooper = robotLooper;

        mRobot = null;
        mCurrentMode = null;
        mLastMode = null;
        mWasModeInitialized = false;
    }

    public LoopingRobotControl(IterativeFrcRobot.Initializer robotInitializer, RobotControl robotControl) {
        this(robotInitializer, robotControl, new NotifierRobotLooper());
    }

    @Override
    public void startCompetition() {
        try {
            mRobot = mRobotInitializer.init(mRobotControl);
        } catch (RobotInitializationException e) {
            throw new RuntimeException(e);
        }

        HAL.observeUserProgramStarting();
        mRobotLooper.startLooping(mRobotControl.getClock(), this::loop);
    }

    @Override
    public void endCompetition() {
        mRobot.robotStop();
        mRobotLooper.stop();
    }

    private void loop() {
        mCurrentMode = mRobotControl.getMode(FrcRobotMode.class);

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
        mode.configureShuffleboardWidgets();

        if (mode.isDisabled()) {
            mRobot.disabledInit();
        } else {
            modeInit(mode);
        }
    }

    private void periodicMode(FrcRobotMode mode) {
        mode.reportModeHal();

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
}
