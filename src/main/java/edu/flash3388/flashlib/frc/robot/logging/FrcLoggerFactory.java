package edu.flash3388.flashlib.frc.robot.logging;

import edu.flash3388.flashlib.util.logging.LogBuildException;
import edu.flash3388.flashlib.util.logging.LogLevel;
import edu.flash3388.flashlib.util.logging.LoggerBuilder;
import edu.flash3388.flashlib.util.logging.jul.JulLoggerAdapter;
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
        if (!logConfiguration.isFileLoggingEnabled()) {
            return createConsoleLogger(logConfiguration);
        }

        try {
            return new LoggerBuilder(LOGGER_NAME)
                    .enableFileLogging(true)
                    .setDateBasedFilesParent(new File(FRC_USER_DIRECTORY))
                    .setTimeBasedFilePattern()
                    .setLogLevel(logConfiguration.isDebug() ? LogLevel.TRACE : LogLevel.INFO)
                    .setLogFileConfig(logConfiguration.getFileConfig())
                    .buildJul();
        } catch (LogBuildException e) {
            DriverStation.reportError("error creating log-based logger: " + e.getMessage(), false);
            return createConsoleLogger(logConfiguration);
        }
    }

    private static java.util.logging.Logger createConsoleLogger(LogConfiguration logConfiguration) {
        return new LoggerBuilder(LOGGER_NAME)
                .enableConsoleLogging(true)
                .setLogLevel(logConfiguration.isDebug() ? LogLevel.TRACE : LogLevel.INFO)
                .buildJul();
    }
}
