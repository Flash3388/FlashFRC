package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;

public class NtpClock implements Clock {

    private final Clock mBaseClock;
    private long mOffsetMillis;

    public NtpClock(Clock baseClock) {
        mBaseClock = baseClock;
        mOffsetMillis = 0;
    }

    @Override
    public Time currentTime() {
        long currentTimeMillis = mBaseClock.currentTime().valueAsMillis();
        return Time.milliseconds(currentTimeMillis + mOffsetMillis);
    }

    void updateOffset(long offsetMillis) {
        mOffsetMillis += offsetMillis;
    }
}
