package com.flash3388.flashlib.frc.robot.hid;

import com.flash3388.flashlib.hid.generic.ChannelType;
import com.flash3388.flashlib.hid.generic.RawHidInterface;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;

public class FrcHidInterface implements RawHidInterface {

    private final DriverStation mDriverStation;

    public FrcHidInterface(DriverStation driverStation) {
        mDriverStation = driverStation;
    }

    @Override
    public boolean hasChannel(int channel) {
        return GenericHID.HIDType.of(mDriverStation.getJoystickType(channel)) != GenericHID.HIDType.kUnknown;
    }

    @Override
    public ChannelType getChannelType(int channel) {
        switch (GenericHID.HIDType.of(mDriverStation.getJoystickType(channel))) {
            case kHIDJoystick:
                return ChannelType.JOYSTICK;
            case kHIDGamepad:
            case kXInputGamepad:
                if (mDriverStation.getJoystickIsXbox(channel)) {
                    return ChannelType.XBOX;
                }
                return ChannelType.JOYSTICK;
            default:
                return ChannelType.HID;
        }
    }

    @Override
    public int getAxesCount(int channel) {
        return mDriverStation.getStickAxisCount(channel);
    }

    @Override
    public int getButtonsCount(int channel) {
        return mDriverStation.getStickButtonCount(channel);
    }

    @Override
    public int getPovsCount(int channel) {
        return mDriverStation.getStickPOVCount(channel);
    }

    @Override
    public double getAxisValue(int channel, int axis) {
        return mDriverStation.getStickAxis(channel, axis);
    }

    @Override
    public boolean getButtonValue(int channel, int button) {
        return mDriverStation.getStickButton(channel, button + 1);
    }

    @Override
    public int getPovValue(int channel, int pov) {
        return mDriverStation.getStickPOV(channel, pov);
    }
}
