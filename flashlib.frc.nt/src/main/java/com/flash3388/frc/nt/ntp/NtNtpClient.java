package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Time;
import edu.wpi.first.networktables.GenericSubscriber;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NtNtpClient {

    private static final int NOT_RUNNING_HANDLE = -1;
    private static final long MINIMAL_REQUEST_PERIOD_MILLIS = 10;

    private final NetworkTableEntry mRequestEntry;

    private final AtomicReference<Thread> mRunningThread;
    private final NtNtpClientSyncer mSyncer;
    private final AtomicInteger mListenerHandle;
    private final AtomicReference<GenericSubscriber> mSubscriber;

    public NtNtpClient(NtpClock clock, NetworkTableEntry requestEntry, NetworkTableEntry requestReceiveTimeEntry, NetworkTableEntry requestSendTimeEntry) {
        mRequestEntry = requestEntry;

        mRunningThread = new AtomicReference<>(null);
        mSyncer = new NtNtpClientSyncer(clock, requestEntry, requestReceiveTimeEntry, requestSendTimeEntry);
        mListenerHandle = new AtomicInteger(NOT_RUNNING_HANDLE);
        mSubscriber = new AtomicReference<>(null);
    }

    public void sync() {
        mSyncer.startSync();
    }

    public boolean isRunning() {
        return mListenerHandle.get() != NOT_RUNNING_HANDLE;
    }

    public void start(Time requestPeriod) {
        if (isRunning()) {
            throw new IllegalStateException("Already Running");
        }
        if (!requestPeriod.isValid() || requestPeriod.valueAsMillis() < MINIMAL_REQUEST_PERIOD_MILLIS) {
            throw new IllegalArgumentException("Invalid request period: " + requestPeriod);
        }

        GenericSubscriber subscriber = mRequestEntry.getTopic().genericSubscribe();
        mSubscriber.set(subscriber);
        int handle = NetworkTableInstance.getDefault().addListener(
                subscriber,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                mSyncer::onResponseReceived);
        mListenerHandle.set(handle);

        Thread syncThread = new Thread(()-> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(requestPeriod.valueAsMillis());
                } catch (InterruptedException e) {
                    break;
                }

                sync();
            }
        }, "sync-thread");
        mRunningThread.set(syncThread);
        syncThread.setDaemon(true);
        syncThread.start();
    }

    public void stop() {
        if (!isRunning()) {
            throw new IllegalStateException("Not Running");
        }

        try {
            Thread syncThread = mRunningThread.getAndSet(null);
            syncThread.interrupt();
            syncThread.join();
        } catch (InterruptedException e) {
        }

        int listenerHandle = mListenerHandle.getAndSet(NOT_RUNNING_HANDLE);
        NetworkTableInstance.getDefault().removeListener(listenerHandle);
        GenericSubscriber subscriber = mSubscriber.getAndSet(null);
        subscriber.close();
    }
}
