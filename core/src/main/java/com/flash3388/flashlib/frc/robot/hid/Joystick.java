package com.flash3388.flashlib.frc.robot.hid;

import com.flash3388.flashlib.robot.hid.Axis;
import com.flash3388.flashlib.robot.hid.GenericHid;
import com.flash3388.flashlib.robot.hid.HidInterface;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;

public class Joystick extends GenericHid {

    private static final int DEFAULT_BUTTON_COUNT = 12;
    private static final int DEFAULT_POV_COUNT = 1;

    public Joystick(Clock clock, HidInterface hidInterface, int channel, int axisCount, int buttonCount, int povsCount, Time buttonPressTime) {
        super(clock, hidInterface, channel, axisCount, buttonCount, povsCount, buttonPressTime);
    }

    public Joystick(int channel, int axisCount, int buttonCount, int povsCount) {
        super(channel, axisCount, buttonCount, povsCount);
    }

    public Joystick(int channel, int buttonCount, int povsCount) {
        super(channel, JoystickAxis.values().length, buttonCount, povsCount);
    }

    public Joystick(int channel) {
        this(channel, DEFAULT_BUTTON_COUNT, DEFAULT_POV_COUNT);
    }

    public Axis getAxis(JoystickAxis axis) {
        return getAxis(axis.axisIndex());
    }
}
