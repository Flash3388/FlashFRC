package com.flash3388.flashlib.frc.robot.dashboard;

import com.beans.util.function.Suppliers;
import com.flash3388.flashlib.scheduling.actions.Action;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SendableActionWrapper implements Sendable {

    private final Action mAction;

    public SendableActionWrapper(Action action) {
        mAction = action;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Command");
        builder.addStringProperty(".name",
                ()-> mAction.getConfiguration().getName(),
                null);
        builder.addBooleanProperty(
                "running",
                mAction::isRunning,
                (value)-> {
                    if (value && !mAction.isRunning()) {
                        mAction.start();
                    } else if (!value && mAction.isRunning()) {
                        mAction.cancel();
                    }
                });
        builder.addBooleanProperty(".isParented",
                Suppliers.of(false),
                null);
    }
}
