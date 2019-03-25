package com.flash3388.flashlib.frc.robot.modes;

import edu.flash3388.flashlib.robot.modes.RobotMode;
import edu.wpi.first.hal.HAL;

public abstract class FrcRobotMode extends RobotMode {

    public static final int DISABLED_KEY = 0;
    public static final int OPERATOR_CONTROL_KEY = 1;
    public static final int AUTONOMOUS_KEY = 2;
    public static final int TEST_KEY = 3;

    public static final FrcRobotMode DISABLED = new FrcRobotMode("DISABLED", DISABLED_KEY) {

        @Override
        public void reportModeHal() {
            HAL.observeUserProgramDisabled();
        }

        @Override
        public boolean isLiveWindowEnabled() {
            return false;
        }
    };

    public static final FrcRobotMode OPERATOR_CONTROL = new FrcRobotMode("OPERATOR_CONTROL", OPERATOR_CONTROL_KEY) {

        @Override
        public void reportModeHal() {
            HAL.observeUserProgramTeleop();
        }

        @Override
        public boolean isLiveWindowEnabled() {
            return false;
        }
    };

    public static final FrcRobotMode AUTONOMOUS = new FrcRobotMode("AUTONOMOUS", AUTONOMOUS_KEY) {

        @Override
        public void reportModeHal() {
            HAL.observeUserProgramAutonomous();
        }

        @Override
        public boolean isLiveWindowEnabled() {
            return false;
        }
    };

    public static final FrcRobotMode TEST = new FrcRobotMode("TEST", TEST_KEY) {

        @Override
        public void reportModeHal() {
            HAL.observeUserProgramTest();
        }

        @Override
        public boolean isLiveWindowEnabled() {
            return true;
        }
    };

    private FrcRobotMode(String name, int key) {
        super(name, key);
    }

    public abstract void reportModeHal();
    public abstract boolean isLiveWindowEnabled();

    public static FrcRobotMode forKey(int key) {
        switch (key) {
            case DISABLED_KEY:
                return DISABLED;
            case OPERATOR_CONTROL_KEY:
                return OPERATOR_CONTROL;
            case AUTONOMOUS_KEY:
                return AUTONOMOUS;
            case TEST_KEY:
                return TEST;
        }

        throw new AssertionError("unexpected key: " + key);
    }
}
