package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.frc.robot.RobotConfiguration;
import com.flash3388.flashlib.frc.robot.base.FrcRobotControlBase;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.RobotInitializationException;
import com.flash3388.flashlib.robot.base.iterative.RobotLooper;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopingRobotControl extends FrcRobotControlBase {

    private final Scheduler mScheduler;
    private final IterativeFrcRobot.Initializer mRobotInitializer;
    private final RobotLooper mRobotLooper;

    private IterativeFrcRobot mRobot;
    private FrcRobotMode mCurrentMode;
    private FrcRobotMode mLastMode;
    private boolean mWasModeInitialized;

    protected LoopingRobotControl(RobotConfiguration configuration, Scheduler scheduler, IterativeFrcRobot.Initializer robotInitializer, RobotLooper robotLooper) {
        super(configuration, scheduler);
        mScheduler = scheduler;
        mRobotInitializer = robotInitializer;
        mRobotLooper = robotLooper;
        init();
    }

    protected LoopingRobotControl(RobotConfiguration configuration, IterativeFrcRobot.Initializer robotInitializer, RobotLooper robotLooper) {
        super(configuration);
        mRobotInitializer = robotInitializer;
        mRobotLooper = robotLooper;
        mScheduler = getScheduler();
        init();
    }

    protected LoopingRobotControl(IterativeFrcRobot.Initializer robotInitializer, RobotLooper robotLooper) {
        this(RobotConfiguration.defaultConfiguration(), robotInitializer, robotLooper);
    }

    @Override
    public void startCompetition() {
        try {
            mRobot = mRobotInitializer.init(this);
        } catch (RobotInitializationException e) {
            throw new RuntimeException(e);
        }

        HAL.observeUserProgramStarting();
        mRobotLooper.doLoop(getClock(), this::loop);
    }

    @Override
    public void endCompetition() {
        mRobot.robotStop();
        mRobotLooper.stopLoop();
    }

    private void loop() {
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
}
