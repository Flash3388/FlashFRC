package com.flash3388.flashlib.frc.robot.logging;

import com.flash3388.flashlib.util.logging.LogBuildException;
import com.flash3388.flashlib.util.logging.LogLevel;
import com.flash3388.flashlib.util.logging.LoggerBuilder;
import com.flash3388.flashlib.util.logging.jul.JulLoggerAdapter;
import edu.wpi.first.wpilibj.DriverStation;
import org.slf4j.Logger;

import java.nio.file.Paths;

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
        LoggerBuilder builder = newLoggerBuilder(logConfiguration);
        if (logConfiguration.isFileLoggingEnabled()) {
            builder.enableFileLogging(true)
                    .setDateBasedFilesParent(Paths.get(FRC_USER_DIRECTORY))
                    .setTimeBasedFilePattern()
                    .setLogFileConfig(logConfiguration.getFileConfig());
        }

        try {
            return builder.buildJul();
        } catch (LogBuildException e) {
            // possible only if using file logging
            DriverStation.reportError("error creating log-based logger: " + e.getMessage(), false);
            return newLoggerBuilder(logConfiguration).buildJul();
        }
    }

    private static LoggerBuilder newLoggerBuilder(LogConfiguration logConfiguration) {
        return new LoggerBuilder(LOGGER_NAME)
                .enableConsoleLogging(false)
                .setLogLevel(logConfiguration.isDebug() ? LogLevel.TRACE : LogLevel.INFO);
    }
}
