package com.flash3388.flashlib.frc.io;

import com.flash3388.flashlib.io.Pwm;
import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.wpilibj.SensorUtil;

public class RoboRioPwm implements Pwm {

    public static final int MAX_RAW = 255;

    private edu.wpi.first.wpilibj.PWM mPwm;

    public RoboRioPwm(edu.wpi.first.wpilibj.PWM pwm) {
        mPwm = pwm;
    }

    public RoboRioPwm(int port) {
        this(new edu.wpi.first.wpilibj.PWM(port));
    }

    @Override
    public void setDuty(double duty) {
        setValue((int) (duty * MAX_RAW));
    }

    @Override
    public double getDuty() {
        return getValue() / (double) MAX_RAW;
    }

    @Override
    public void setValue(int value) {
        mPwm.setRaw(value);
    }

    @Override
    public int getValue() {
        return mPwm.getRaw();
    }

    @Override
    public int getMaxValue() {
        return MAX_RAW;
    }

    @Override
    public void setFrequency(double frequency) {
        throw new UnsupportedOperationException("cannot set frequency");
    }

    @Override
    public double getFrequency() {
        return SensorUtil.kSystemClockTicksPerMicrosecond * 1e3 / DIOJNI.getLoopTiming();
    }

    @Override
    public void close() {
        if (mPwm == null) {
            return;
        }

        mPwm.close();
        mPwm = null;
    }
}
