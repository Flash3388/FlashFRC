package com.flash3388.flashlib.frc.io.devices;

public interface CTREPidController {

    void setP(double kp);
    void setI(double ki);
    void setD(double kd);
    void setF(double kf);
}
