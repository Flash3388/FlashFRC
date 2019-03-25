package com.flash3388.flashlib.frc.robot.logging;

import edu.flash3388.flashlib.util.logging.LogFileConfig;

public class LogConfiguration {

    private static final int DEFAULT_FILE_SIZE_BYTES = 1048576; // 1MB
    private static final int DEFAULT_FILE_COUNT = 10;

    private final LogFileConfig mFileConfig;
    private final boolean mFileLogging;
    private final boolean mDebug;

    public LogConfiguration(LogFileConfig fileConfig, boolean fileLogging, boolean debug) {
        mFileConfig = fileConfig;
        mFileLogging = fileLogging;
        mDebug = debug;
    }

    public LogConfiguration(boolean fileLogging, boolean debug) {
        this(new LogFileConfig(
                debug ? DEFAULT_FILE_SIZE_BYTES * 2 : DEFAULT_FILE_SIZE_BYTES,
                DEFAULT_FILE_COUNT),
                fileLogging, debug);
    }

    public static LogConfiguration defaultConfiguration() {
        return new LogConfiguration(false, true);
    }

    public LogFileConfig getFileConfig() {
        return mFileConfig;
    }

    public boolean isFileLoggingEnabled() {
        return mFileLogging;
    }

    public boolean isDebug() {
        return mDebug;
    }
}
