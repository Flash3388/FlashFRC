package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;

import java.util.concurrent.atomic.AtomicLong;

public class NtpClock implements Clock {

    private final Clock mBaseClock;
    private final AtomicLong mOffsetMillis;

    public NtpClock(Clock baseClock) {
        mBaseClock = baseClock;
        mOffsetMillis = new AtomicLong(0);
    }

    @Override
    public Time currentTime() {
        long currentTimeMillis = mBaseClock.currentTime().valueAsMillis();
        return Time.milliseconds(currentTimeMillis + mOffsetMillis.get());
    }

    void updateOffset(long offsetMillis) {
        mOffsetMillis.addAndGet(offsetMillis);
    }
}
