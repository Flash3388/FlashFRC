package com.flash3388.flashlib.frc.robot.logging;

import edu.wpi.first.wpilibj.DriverStation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class DriverStationLoggerHandler extends Handler {

    @Override
    public void publish(LogRecord record) {
        String log = String.format("[%s] (%s:%s): %s%s",
                record.getMillis(),
                record.getSourceClassName(), record.getSourceMethodName(),
                record.getMessage(),
                formatThrowable(record.getThrown()));

        if (record.getLevel() == Level.WARNING) {
            DriverStation.reportWarning(log, false);
        } else if (record.getLevel() == Level.SEVERE) {
            DriverStation.reportError(log, false);
        } else {
            System.out.println(String.format("%s> <%s> %s",
                    record.getLoggerName(), record.getLevel(), log));
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

    private String formatThrowable(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringWriter stringWriter = new StringWriter();
        stringWriter.append("\n\t");

        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        return stringWriter.toString();
    }
}
