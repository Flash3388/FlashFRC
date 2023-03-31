package com.flash3388.flashlib.frc.robot.io.devices;

import com.flash3388.flashlib.frc.io.devices.impl.FrcSpeedController;
import com.flash3388.flashlib.io.devices.SpeedController;
import com.flash3388.flashlib.io.devices.SpeedControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.DMC60;
import edu.wpi.first.wpilibj.motorcontrol.Jaguar;
import edu.wpi.first.wpilibj.motorcontrol.NidecBrushless;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj.motorcontrol.PWMVenom;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.SD540;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SpeedControllers {

    private final Collection<SpeedController> mControllers;
    private final Collection<edu.wpi.first.wpilibj.motorcontrol.MotorController> mControllersFrc;

    public SpeedControllers() {
        mControllers = new ArrayList<>();
        mControllersFrc = new ArrayList<>();
    }

    public SpeedControllers add(SpeedController... speedControllers) {
        mControllers.addAll(Arrays.asList(speedControllers));
        return this;
    }

    public SpeedControllers add(edu.wpi.first.wpilibj.motorcontrol.MotorController... speedControllers) {
        mControllersFrc.addAll(Arrays.asList(speedControllers));
        return this;
    }

    public SpeedControllers addTalon(int channel) {
        return add(new Talon(channel));
    }

    public SpeedControllers addVictor(int channel) {
        return add(new Victor(channel));
    }

    public SpeedControllers addVictorSP(int channel) {
        return add(new VictorSP(channel));
    }

    public SpeedControllers addSpark(int channel) {
        return add(new Spark(channel));
    }

    public SpeedControllers addSD540(int channel) {
        return add(new SD540(channel));
    }

    public SpeedControllers addJaguar(int channel) {
        return add(new Jaguar(channel));
    }

    public SpeedControllers addDMC60(int channel) {
        return add(new DMC60(channel));
    }

    public SpeedControllers addNidecBrushless(int pwmChannel, int dioChannel) {
        return add(new NidecBrushless(pwmChannel, dioChannel));
    }

    public SpeedControllers addPwmSparkMax(int channel) {
        return add(new PWMSparkMax(channel));
    }

    public SpeedControllers addPwmTalonFX(int channel) {
        return add(new PWMTalonFX(channel));
    }

    public SpeedControllers addPwmVenom(int channel) {
        return add(new PWMVenom(channel));
    }

    public SpeedControllers addPwmVictorSPX(int channel) {
        return add(new PWMVictorSPX(channel));
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
