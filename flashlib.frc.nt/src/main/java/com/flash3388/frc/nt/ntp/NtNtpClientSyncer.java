package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Time;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.concurrent.atomic.AtomicReference;

class NtNtpClientSyncer {

    private final NtpClock mClock;

    private final NetworkTableEntry mRequestEntry;
    private final NetworkTableEntry mRequestReceiveTimeEntry;
    private final NetworkTableEntry mRequestSendTimeEntry;

    private final AtomicReference<Time> mClientRequestStart;

    public NtNtpClientSyncer(NtpClock clock, NetworkTableEntry requestEntry, NetworkTableEntry requestReceiveTimeEntry, NetworkTableEntry requestSendTimeEntry) {
        mClock = clock;
        mRequestEntry = requestEntry;
        mRequestReceiveTimeEntry = requestReceiveTimeEntry;
        mRequestSendTimeEntry = requestSendTimeEntry;

        mClientRequestStart = new AtomicReference<>();
    }

    void startSync() {
        if (mRequestEntry.getBoolean(false)) {
            return; // already syncing
        }

        mRequestEntry.setBoolean(true);
        mClientRequestStart.set(mClock.currentTime());
    }

    void onResponseReceived(EntryNotification notification) {
        if (notification.value.getBoolean()) {
            return;
        }

        Time clientStartTime = mClientRequestStart.getAndSet(Time.INVALID);
        long clientStartTimestamp = clientStartTime.isValid() ? clientStartTime.valueAsMillis() : 0;
        long clientEndTimestamp = mClock.currentTime().valueAsMillis();
        long serverStartTimestamp = (long) mRequestReceiveTimeEntry.getDouble(0.0);
        long serverEndTimestamp = (long) mRequestSendTimeEntry.getDouble(0.0);

        long offset = ((serverStartTimestamp - clientStartTimestamp) + (serverEndTimestamp - clientEndTimestamp)) / 2;
        mClock.updateOffset(offset);
    }
}
