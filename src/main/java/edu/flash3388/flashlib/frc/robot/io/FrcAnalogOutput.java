package edu.flash3388.flashlib.frc.robot.io;

import edu.flash3388.flashlib.robot.io.AnalogOutput;

public class FrcAnalogOutput implements AnalogOutput {

    private edu.wpi.first.wpilibj.AnalogOutput mAnalogOutput;

    public FrcAnalogOutput(edu.wpi.first.wpilibj.AnalogOutput analogOutput) {
        mAnalogOutput = analogOutput;
    }

    public FrcAnalogOutput(int port) {
        this(new edu.wpi.first.wpilibj.AnalogOutput(port));
    }

    @Override
    public void setValue(int value) {
        setVoltage(value / (double) FrcAnalogPort.MAX_VALUE * FrcAnalogPort.MAX_VOLTAGE);
    }

    @Override
    public void setVoltage(double voltage) {
        mAnalogOutput.setVoltage(voltage);
    }

    @Override
    public int getValue() {
        return (int) (getVoltage() / FrcAnalogPort.MAX_VOLTAGE * FrcAnalogPort.MAX_VALUE);
    }

    @Override
    public double getVoltage() {
        return mAnalogOutput.getVoltage();
    }

    @Override
    public void free() {
        if (mAnalogOutput == null) {
            return;
        }

        mAnalogOutput.close();
        mAnalogOutput = null;
    }
}
