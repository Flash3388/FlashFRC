package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.io.AnalogInput;
import com.flash3388.flashlib.io.AnalogOutput;
import com.flash3388.flashlib.io.DigitalInput;
import com.flash3388.flashlib.io.DigitalOutput;
import com.flash3388.flashlib.io.IoChannel;
import com.flash3388.flashlib.io.IoInterface;
import com.flash3388.flashlib.io.Pwm;

public class RoboRioIoInterface implements IoInterface {

    @Override
    public AnalogInput newAnalogInput(IoChannel channel) {
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioAnalogInput(roboRioChannel.getChannelNumber());
    }

    @Override
    public AnalogOutput newAnalogOutput(IoChannel channel) {
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioAnalogOutput(roboRioChannel.getChannelNumber());
    }

    @Override
    public DigitalInput newDigitalInput(IoChannel channel) {
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioDigitalInput(roboRioChannel.getChannelNumber());
    }

    @Override
    public DigitalOutput newDigitalOutput(IoChannel channel) {
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioDigitalOutput(roboRioChannel.getChannelNumber());
    }

    @Override
    public Pwm newPwm(IoChannel channel) {
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioPwm(roboRioChannel.getChannelNumber());
    }
}
