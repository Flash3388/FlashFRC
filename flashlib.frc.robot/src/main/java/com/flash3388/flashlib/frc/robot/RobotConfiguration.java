package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.logging.LogConfiguration;

public class RobotConfiguration {

    private final LogConfiguration mLogConfiguration;

    public RobotConfiguration(LogConfiguration logConfiguration) {
        mLogConfiguration = logConfiguration;
    }

    public static RobotConfiguration defaultConfiguration() {
        return new RobotConfiguration(LogConfiguration.defaultConfiguration());
    }

    public static RobotConfiguration competitionConfiguration() {
        return new RobotConfiguration(LogConfiguration.competitionConfiguration());
    }

    public static RobotConfiguration debugConfiguration() {
        return new RobotConfiguration(LogConfiguration.debugConfiguration());
    }

    public LogConfiguration getLogConfiguration() {
        return mLogConfiguration;
    }
}
