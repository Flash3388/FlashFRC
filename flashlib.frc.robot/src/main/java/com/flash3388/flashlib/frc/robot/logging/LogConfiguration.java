package com.flash3388.flashlib.frc.robot.logging;

import com.flash3388.flashlib.util.logging.LogFileConfig;

public class LogConfiguration {

    private static final int DEFAULT_FILE_SIZE_BYTES = 1048576; // 1MB
    private static final int DEFAULT_FILE_COUNT = 10;

    private final LogFileConfig mFileConfig;
    private final boolean mAllowFileLogging;
    private final boolean mDebug;

    public LogConfiguration(LogFileConfig fileConfig, boolean allowFileLogging, boolean debug) {
        mFileConfig = fileConfig;
        mAllowFileLogging = allowFileLogging;
        mDebug = debug;
    }

    public LogConfiguration(boolean allowFileLogging, boolean debug) {
        this(new LogFileConfig(
                debug ? DEFAULT_FILE_SIZE_BYTES * 2 : DEFAULT_FILE_SIZE_BYTES,
                DEFAULT_FILE_COUNT),
                allowFileLogging, debug);
    }

    public static LogConfiguration defaultConfiguration() {
        return new LogConfiguration(false, false);
    }

    public static LogConfiguration competitionConfiguration() {
        return new LogConfiguration(true, false);
    }

    public static LogConfiguration debugConfiguration() {
        return new LogConfiguration(false, true);
    }

    public LogFileConfig getFileConfig() {
        return mFileConfig;
    }

    public boolean isFileLoggingEnabled() {
        return mAllowFileLogging;
    }

    public boolean isDebug() {
        return mDebug;
    }
}
