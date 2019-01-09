package edu.flash3388.flashlib.frc.robot.logging;

import edu.flash3388.flashlib.util.logging.Logging;
import edu.wpi.first.wpilibj.DriverStation;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FrcLoggerFactory {

    private static final String FRC_USER_DIRECTORY = "/home/lvuser";

    private FrcLoggerFactory() {}

    public static Logger createLogger() {
        Logger logger;

        try {
            logger = Logging.getLogger(new File(FRC_USER_DIRECTORY), "robot");
        } catch (IOException e) {
            DriverStation.reportError(String.format("Error while initializing logger: %s", e.getMessage()), false);
            logger = Logger.getLogger("robot");
        }

        logger.addHandler(new DriverStationLoggerHandler());

        return logger;
    }
}
