package com.flash3388.flashlib.frc.robot.hid;

import com.flash3388.flashlib.hid.generic.ChannelType;
import com.flash3388.flashlib.hid.generic.RawHidInterface;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

public class FrcHidInterface implements RawHidInterface {

    @Override
    public boolean hasChannel(int channel) {
        return GenericHID.HIDType.of(DriverStation.getJoystickType(channel)) != GenericHID.HIDType.kUnknown;
    }

    @Override
    public ChannelType getChannelType(int channel) {
        switch (GenericHID.HIDType.of(DriverStation.getJoystickType(channel))) {
            case kHIDJoystick:
                return ChannelType.JOYSTICK;
            case kHIDGamepad:
            case kXInputGamepad:
                if (DriverStation.getJoystickIsXbox(channel)) {
                    return ChannelType.XBOX;
                }
                return ChannelType.JOYSTICK;
            default:
                return ChannelType.HID;
        }
    }

    @Override
    public int getAxesCount(int channel) {
        return DriverStation.getStickAxisCount(channel);
    }

    @Override
    public int getButtonsCount(int channel) {
        return DriverStation.getStickButtonCount(channel);
    }

    @Override
    public int getPovsCount(int channel) {
        return DriverStation.getStickPOVCount(channel);
    }

    @Override
    public double getAxisValue(int channel, int axis) {
        return DriverStation.getStickAxis(channel, axis);
    }

    @Override
    public boolean getButtonValue(int channel, int button) {
        return DriverStation.getStickButton(channel, button + 1);
    }

    @Override
    public int getPovValue(int channel, int pov) {
        return DriverStation.getStickPOV(channel, pov);
    }
}
