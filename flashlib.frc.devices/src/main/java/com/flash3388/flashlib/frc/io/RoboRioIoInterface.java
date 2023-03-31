package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.AnalogInput;
import com.flash3388.flashlib.io.AnalogOutput;
import com.flash3388.flashlib.io.DigitalInput;
import com.flash3388.flashlib.io.DigitalOutput;
import com.flash3388.flashlib.io.IoChannel;
import com.flash3388.flashlib.io.IoInterface;
import com.flash3388.flashlib.io.Pwm;
import com.flash3388.flashlib.util.FlashLibMainThread;

public class RoboRioIoInterface implements IoInterface {

    private final FlashLibMainThread mMainThread;

    public RoboRioIoInterface(FlashLibMainThread mainThread) {
        mMainThread = mainThread;
    }

    @Override
    public AnalogInput newAnalogInput(IoChannel channel) {
        mMainThread.verifyCurrentThread();
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioAnalogInput(roboRioChannel.getChannelNumber());
    }

    @Override
    public AnalogOutput newAnalogOutput(IoChannel channel) {
        mMainThread.verifyCurrentThread();
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioAnalogOutput(roboRioChannel.getChannelNumber());
    }

    @Override
    public DigitalInput newDigitalInput(IoChannel channel) {
        mMainThread.verifyCurrentThread();
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioDigitalInput(roboRioChannel.getChannelNumber());
    }

    @Override
    public DigitalOutput newDigitalOutput(IoChannel channel) {
        mMainThread.verifyCurrentThread();
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioDigitalOutput(roboRioChannel.getChannelNumber());
    }

    @Override
    public Pwm newPwm(IoChannel channel) {
        mMainThread.verifyCurrentThread();
        RoboRioChannel roboRioChannel = IoChannel.cast(channel, RoboRioChannel.class);
        return new RoboRioPwm(roboRioChannel.getChannelNumber());
    }
}
