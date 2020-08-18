package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.io.IoChannel;

public class RoboRioChannel implements IoChannel {

    private final int mChannelConnection;

    public RoboRioChannel(int channelConnection) {
        mChannelConnection = channelConnection;
    }

    public int getChannelNumber() {
        return mChannelConnection;
    }
}
