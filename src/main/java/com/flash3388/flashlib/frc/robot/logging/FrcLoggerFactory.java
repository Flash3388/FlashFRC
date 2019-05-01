package com.flash3388.flashlib.frc.robot.logging;

import com.flash3388.flashlib.util.logging.LogBuildException;
import com.flash3388.flashlib.util.logging.LogLevel;
import com.flash3388.flashlib.util.logging.LoggerBuilder;
import com.flash3388.flashlib.util.logging.jul.JulLoggerAdapter;
import edu.wpi.first.wpilibj.DriverStation;
import org.slf4j.Logger;

import java.io.File;

public class FrcLoggerFactory {

    private static final String FRC_USER_DIRECTORY = "/home/lvuser";
    private static final String LOGGER_NAME = "robot";

    private FrcLoggerFactory() {}

    public static Logger createLogger(LogConfiguration logConfiguration) {
        java.util.logging.Logger logger = createBaseLogger(logConfiguration);
        logger.addHandler(new DriverStationLoggerHandler());

        return new JulLoggerAdapter(logger);
    }

    private static java.util.logging.Logger createBaseLogger(LogConfiguration logConfiguration) {
        LoggerBuilder builder = createConsoleLoggerBuilder(logConfiguration);
        if (logConfiguration.isFileLoggingEnabled()) {
            builder.enableFileLogging(true)
                    .setDateBasedFilesParent(new File(FRC_USER_DIRECTORY))
                    .setTimeBasedFilePattern()
                    .setLogFileConfig(logConfiguration.getFileConfig());
        }

        try {
            return builder.buildJul();
        } catch (LogBuildException e) {
            // possible only if using file logging
            DriverStation.reportError("error creating log-based logger: " + e.getMessage(), false);
            return createConsoleLoggerBuilder(logConfiguration).buildJul();
        }
    }

    private static LoggerBuilder createConsoleLoggerBuilder(LogConfiguration logConfiguration) {
        return new LoggerBuilder(LOGGER_NAME)
                .enableConsoleLogging(logConfiguration.isConsoleLoggingEnabled())
                .setLogLevel(logConfiguration.isDebug() ? LogLevel.TRACE : LogLevel.INFO);
    }
}
