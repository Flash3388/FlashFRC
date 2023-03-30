package com.flash3388.flashlib.frc.robot.logging;


import edu.wpi.first.wpilibj.DriverStation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Plugin(
        name = "DriverStation",
        category = "Core",
        elementType = Appender.ELEMENT_TYPE,
        printObject = true
)
public class DriverStationAppender extends AbstractAppender {

    public DriverStationAppender(String name, Filter filter, PatternLayout layout) {
        super(name, filter, layout);
    }

    @PluginFactory
    public static DriverStationAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("PatternLayout") PatternLayout patternLayout) {
        return new DriverStationAppender(name, filter, patternLayout);
    }

    @Override
    public void append(LogEvent event) {
        byte[] bytes = getLayout().toByteArray(event);
        if (event.getLevel().isLessSpecificThan(Level.WARN)) {
            try {
                System.out.write(bytes);
            } catch (IOException e) {
                throw new AppenderLoggingException(e);
            }
        } else {
            String msg = new String(bytes, StandardCharsets.UTF_8);

            if (event.getLevel() == Level.WARN) {
                DriverStation.reportWarning(msg, false);
            } else {
                DriverStation.reportError(msg, false);
            }
        }
    }
}
