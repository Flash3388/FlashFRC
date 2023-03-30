package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.app.net.NetworkConfiguration;

public class RobotConfiguration {

    private final NetworkConfiguration mNetworkConfiguration;

    public RobotConfiguration(NetworkConfiguration networkConfiguration) {
        mNetworkConfiguration = networkConfiguration;
    }

    public NetworkConfiguration getNetworkConfiguration() {
        return mNetworkConfiguration;
    }

    public static RobotConfiguration defaultConfiguration() {
        return new RobotConfiguration(NetworkConfiguration.disabled());
    }
}
