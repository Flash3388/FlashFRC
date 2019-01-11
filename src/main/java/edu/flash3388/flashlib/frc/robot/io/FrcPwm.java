package edu.flash3388.flashlib.frc.robot.io;

import edu.flash3388.flashlib.robot.io.Pwm;
import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SensorUtil;

public class FrcPwm implements Pwm {

    public static final int MAX_RAW = 255;

    private edu.wpi.first.wpilibj.PWM mPwm;

    public FrcPwm(PWM pwm) {
        mPwm = pwm;
    }

    public FrcPwm(int port) {
        this(new edu.wpi.first.wpilibj.PWM(port));
    }

    @Override
    public void setDuty(double duty) {
        setRaw((int) (duty * MAX_RAW));
    }

    @Override
    public void setRaw(int raw) {
        mPwm.setRaw(raw);
    }

    @Override
    public double getDuty() {
        return getRaw() / (double) MAX_RAW;
    }

    @Override
    public int getRaw() {
        return mPwm.getRaw();
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
    public void free() {
        if (mPwm == null) {
            return;
        }

        mPwm.close();
        mPwm = null;
    }
}