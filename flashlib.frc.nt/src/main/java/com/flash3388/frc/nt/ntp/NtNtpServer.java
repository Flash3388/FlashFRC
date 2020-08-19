package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NtNtpServer {

    private static final int NOT_RUNNING_HANDLE = -1;

    private final Clock mClock;
    private final NetworkTableEntry mRequestEntry;
    private final NetworkTableEntry mRequestReceiveTimeEntry;
    private final NetworkTableEntry mRequestSendTimeEntry;

    private final AtomicInteger mListenerHandle;

    public NtNtpServer(Clock clock, NetworkTableEntry requestEntry, NetworkTableEntry requestReceiveTimeEntry, NetworkTableEntry requestSendTimeEntry) {
        mClock = clock;

        mRequestEntry = requestEntry;
        mRequestEntry.setBoolean(false);

        mRequestReceiveTimeEntry = requestReceiveTimeEntry;
        mRequestReceiveTimeEntry.setDouble(0.0);

        mRequestSendTimeEntry = requestSendTimeEntry;
        mRequestSendTimeEntry.setDouble(0.0);

        mListenerHandle = new AtomicInteger(NOT_RUNNING_HANDLE);
    }

    public boolean isRunning() {
        return mListenerHandle.get() != NOT_RUNNING_HANDLE;
    }

    public void start() {
        if (isRunning()) {
            throw new IllegalStateException("Already Running");
        }

        int handle = mRequestEntry.addListener(this::onRequestReceived, EntryListenerFlags.kUpdate);
        mListenerHandle.set(handle);
    }

    public void stop() {
        if (!isRunning()) {
            throw new IllegalStateException("Not Running");
        }

        int listenerHandle = mListenerHandle.getAndSet(NOT_RUNNING_HANDLE);
        mRequestEntry.removeListener(listenerHandle);
    }

    private void onRequestReceived(EntryNotification notification) {
        if (!notification.value.getBoolean()) {
            return;
        }

        long receiveTimestamp = mClock.currentTime().toUnit(TimeUnit.MILLISECONDS).value();
        mRequestReceiveTimeEntry.setDouble(receiveTimestamp);

        long sendTimestamp = mClock.currentTime().toUnit(TimeUnit.MILLISECONDS).value();
        mRequestSendTimeEntry.setDouble(sendTimestamp);

        mRequestEntry.setBoolean(false);
    }
}
