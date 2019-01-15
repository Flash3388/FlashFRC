package edu.flash3388.flashlib.frc.robot;

import edu.flash3388.flashlib.frc.robot.logging.LogConfiguration;

public class RobotConfiguration {

    private final LogConfiguration mLogConfiguration;

    public RobotConfiguration(LogConfiguration logConfiguration) {
        mLogConfiguration = logConfiguration;
    }

    public static RobotConfiguration defaultConfiguration() {
        return new RobotConfiguration(LogConfiguration.defaultConfiguration());
    }

    public LogConfiguration getLogConfiguration() {
        return mLogConfiguration;
    }
}
