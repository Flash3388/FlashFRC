package com.flash3388.flashlib.frc.io.devices.ctre;

public interface CTRELimitSwitch {

    enum Source {
        REMOTE_TALON,
        REMOTE_CANIFIER,
        REMOTE_CANCODER
    }

    boolean isClosed();

    void configureIntegrated(boolean normallyOpen);
    void configure(Source source, boolean normallyOpen, int deviceId);
    void disable();
}
