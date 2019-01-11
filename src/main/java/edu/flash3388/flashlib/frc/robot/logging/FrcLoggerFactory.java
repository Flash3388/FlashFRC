package edu.flash3388.flashlib.frc.robot.logging;

import edu.flash3388.flashlib.util.logging.Logging;
import edu.flash3388.flashlib.util.logging.jul.JulLoggerAdapter;
import edu.wpi.first.wpilibj.DriverStation;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class FrcLoggerFactory {

    private static final String FRC_USER_DIRECTORY = "/home/lvuser";

    private FrcLoggerFactory() {}

    public static Logger createLogger() {
        java.util.logging.Logger logger;

        try {
            logger = Logging.getJulLogger(new File(FRC_USER_DIRECTORY), "robot");
        } catch (IOException e) {
            DriverStation.reportError(String.format("Error while initializing logger: %s", e.getMessage()), false);
            logger = Logging.getJulConsoleLogger("robot");
        }

        logger.addHandler(new DriverStationLoggerHandler());

        return new JulLoggerAdapter(logger);
    }
}
