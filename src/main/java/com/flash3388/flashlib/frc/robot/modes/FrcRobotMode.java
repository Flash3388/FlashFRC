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

    public static FrcRobotMode forKey(int key) {
        for (FrcRobotMode mode : FrcRobotMode.values()) {
            if (mode.getKey() == key) {
                return mode;
            }
        }

        throw new AssertionError("unexpected key: " + key);
    }

    public static FrcRobotMode cast(RobotMode mode) {
        if (mode instanceof FrcRobotMode) {
            return (FrcRobotMode) mode;
        }

        return forKey(mode.getKey());
    }
}
