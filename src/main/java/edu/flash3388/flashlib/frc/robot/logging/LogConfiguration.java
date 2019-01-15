package edu.flash3388.flashlib.frc.robot.logging;

import edu.flash3388.flashlib.util.logging.LogFileConfig;

public class LogConfiguration {

    private static final int DEFAULT_FILE_SIZE_BYTES = 1048576; // 1MB
    private static final int DEFAULT_FILE_COUNT = 10;

    private final LogFileConfig mFileConfig;
    private final boolean mDebug;

    public LogConfiguration(LogFileConfig fileConfig, boolean debug) {
        mFileConfig = fileConfig;
        mDebug = debug;
    }

    public LogConfiguration(boolean debug) {
        this(new LogFileConfig(
                debug ? DEFAULT_FILE_SIZE_BYTES * 2 : DEFAULT_FILE_SIZE_BYTES,
                DEFAULT_FILE_COUNT),
                debug);
    }

    public static LogConfiguration defaultConfiguration() {
        return new LogConfiguration(false);
    }

    public LogFileConfig getFileConfig() {
        return mFileConfig;
    }

    public boolean isDebug() {
        return mDebug;
    }
}
