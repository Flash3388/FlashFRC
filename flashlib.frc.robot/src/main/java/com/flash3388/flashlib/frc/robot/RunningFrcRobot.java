package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.global.GlobalDependencies;
import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.RunningRobot;

import java.util.concurrent.atomic.AtomicReference;

public class RunningFrcRobot {

    private RunningFrcRobot() {}

    private static final AtomicReference<FrcRobotControl> sControlInstance = new AtomicReference<>(null);

    public static FrcRobotControl getControl() {
        FrcRobotControl control = sControlInstance.get();
        if (control == null) {
            throw new IllegalStateException("no robotcontrol was set");
        }

        return control;
    }

    public static void setControlInstance(FrcRobotControl instance) {
        RobotControl previousInstance = sControlInstance.getAndSet(instance);

        RunningRobot.setControlInstance(instance);

        if (previousInstance != null) {
            previousInstance.getLogger().warn("RunningRobot instance replaced");
        }
    }
}
