package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.networktables.GenericSubscriber;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NtNtpServer {

    private static final int NOT_RUNNING_HANDLE = -1;

    private final Clock mClock;
    private final NetworkTableEntry mRequestEntry;
    private final NetworkTableEntry mRequestReceiveTimeEntry;
    private final NetworkTableEntry mRequestSendTimeEntry;

    private final AtomicInteger mListenerHandle;
    private final AtomicReference<GenericSubscriber> mSubscriber;

    public NtNtpServer(Clock clock, NetworkTableEntry requestEntry, NetworkTableEntry requestReceiveTimeEntry, NetworkTableEntry requestSendTimeEntry) {
        mClock = clock;

        mRequestEntry = requestEntry;
        mRequestEntry.setBoolean(false);

        mRequestReceiveTimeEntry = requestReceiveTimeEntry;
        mRequestReceiveTimeEntry.setDouble(0.0);

        mRequestSendTimeEntry = requestSendTimeEntry;
        mRequestSendTimeEntry.setDouble(0.0);

        mListenerHandle = new AtomicInteger(NOT_RUNNING_HANDLE);
        mSubscriber = new AtomicReference<>(null);
    }

    public boolean isRunning() {
        return mListenerHandle.get() != NOT_RUNNING_HANDLE;
    }

    public void start() {
        if (isRunning()) {
            throw new IllegalStateException("Already Running");
        }

        GenericSubscriber subscriber = mRequestEntry.getTopic().genericSubscribe();
        mSubscriber.set(subscriber);
        int handle = NetworkTableInstance.getDefault().addListener(
                subscriber,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                this::onRequestReceived);
        mListenerHandle.set(handle);
    }

    public void stop() {
        if (!isRunning()) {
            throw new IllegalStateException("Not Running");
        }

        int listenerHandle = mListenerHandle.getAndSet(NOT_RUNNING_HANDLE);
        NetworkTableInstance.getDefault().removeListener(listenerHandle);
        GenericSubscriber subscriber = mSubscriber.getAndSet(null);
        subscriber.close();
    }

    private void onRequestReceived(NetworkTableEvent event) {
        if (!event.valueData.value.getBoolean()) {
            return;
        }

        long receiveTimestamp = mClock.currentTime().toUnit(TimeUnit.MILLISECONDS).value();
        mRequestReceiveTimeEntry.setDouble(receiveTimestamp);

        long sendTimestamp = mClock.currentTime().toUnit(TimeUnit.MILLISECONDS).value();
        mRequestSendTimeEntry.setDouble(sendTimestamp);

        mRequestEntry.setBoolean(false);
    }
}
