package com.flash3388.flashlib.frc.robot.hid;

public enum JoystickAxis {
    X(0),
    Y(1),
    Z(2),
    THROTTLE(3);

    private final int mAxisIndex;

    JoystickAxis(int axisIndex) {
        mAxisIndex = axisIndex;
    }

    public int axisIndex() {
        return mAxisIndex;
    }
}
