package com.flash3388.flashlib.frc.robot.io;

import com.flash3388.flashlib.io.AnalogOutput;

public class RoboRioAnalogOutput implements AnalogOutput {

    private edu.wpi.first.wpilibj.AnalogOutput mAnalogOutput;

    public RoboRioAnalogOutput(edu.wpi.first.wpilibj.AnalogOutput analogOutput) {
        mAnalogOutput = analogOutput;
    }

    public RoboRioAnalogOutput(int port) {
        this(new edu.wpi.first.wpilibj.AnalogOutput(port));
    }

    @Override
    public void setValue(int value) {
        setVoltage(value / (double) RoboRioAnalogPort.MAX_VALUE * RoboRioAnalogPort.MAX_VOLTAGE);
    }

    @Override
    public void setVoltage(double voltage) {
        mAnalogOutput.setVoltage(voltage);
    }

    @Override
    public int getValue() {
        return (int) (getVoltage() / RoboRioAnalogPort.MAX_VOLTAGE * RoboRioAnalogPort.MAX_VALUE);
    }

    @Override
    public double getVoltage() {
        return mAnalogOutput.getVoltage();
    }

    @Override
    public void close() {
        if (mAnalogOutput == null) {
            return;
        }

        mAnalogOutput.close();
        mAnalogOutput = null;
    }
}
