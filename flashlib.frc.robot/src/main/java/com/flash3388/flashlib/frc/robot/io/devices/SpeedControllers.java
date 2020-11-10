package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.io.devices.SpeedControllerGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SpeedControllers {

    private final Collection<SpeedController> mControllers;
    private final Collection<edu.wpi.first.wpilibj.SpeedController> mControllersFrc;

    public SpeedControllers() {
        mControllers = new ArrayList<>();
        mControllersFrc = new ArrayList<>();
    }

    public SpeedControllers add(SpeedController... speedControllers) {
        mControllers.addAll(Arrays.asList(speedControllers));
        return this;
    }

    public SpeedControllers add(edu.wpi.first.wpilibj.SpeedController... speedControllers) {
        mControllersFrc.addAll(Arrays.asList(speedControllers));
        return this;
    }

    public SpeedController build() {
        if (mControllersFrc.size() > 1) {
            mControllers.add(new FrcSpeedControllerGroup(mControllersFrc));
        } else if (mControllersFrc.size() == 1) {
            mControllers.add(new FrcSpeedController(mControllersFrc.iterator().next()));
        }

        if (mControllers.size() > 1) {
            return new SpeedControllerGroup(mControllers);
        } else if (mControllers.size() == 1) {
            return mControllers.iterator().next();
        }

        throw new IllegalStateException("No controllers defined");
    }
}
