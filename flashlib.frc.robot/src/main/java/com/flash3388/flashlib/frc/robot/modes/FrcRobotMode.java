package com.flash3388.flashlib.frc.robot.modes;

import com.flash3388.flashlib.robot.modes.RobotMode;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public enum FrcRobotMode implements RobotMode {
    DISABLED(RobotMode.DISABLED.getKey(), true, false) {
        @Override
        public void reportModeHal() {
            HAL.observeUserProgramDisabled();
        }
        @Override
        public void configureShuffleboardWidgets() {
            Shuffleboard.disableActuatorWidgets();
        }
    },
    OPERATOR_CONTROL(1, false, false) {
        @Override
        public void reportModeHal() {
            HAL.observeUserProgramTeleop();
        }
        @Override
        public void configureShuffleboardWidgets() {
            Shuffleboard.disableActuatorWidgets();
        }
    },
    AUTONOMOUS(2, false, false) {
        @Override
        public void reportModeHal() {
            HAL.observeUserProgramAutonomous();
        }
        @Override
        public void configureShuffleboardWidgets() {
            Shuffleboard.disableActuatorWidgets();
        }
    },
    TEST(3, false, true) {
        @Override
        public void reportModeHal() {
            HAL.observeUserProgramTest();
        }
        @Override
        public void configureShuffleboardWidgets() {
            Shuffleboard.enableActuatorWidgets();
        }
    };

    private final int mKey;
    private final boolean mIsDisabled;
    private final boolean mIsLiveWindowEnabled;

    FrcRobotMode(int key, boolean isDisabled, boolean isLiveWindowEnabled) {
        mKey = key;
        mIsDisabled = isDisabled;
        mIsLiveWindowEnabled = isLiveWindowEnabled;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public int getKey() {
        return mKey;
    }

    @Override
    public boolean isDisabled() {
        return mIsDisabled;
    }

    public boolean isLiveWindowEnabled() {
        return mIsLiveWindowEnabled;
    }

    public abstract void reportModeHal();
    public abstract void configureShuffleboardWidgets();
}
