package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.vision.processing.analysis.Analysis;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

public class RemoveVisionClient implements AutoCloseable {

    private final NetworkTable mAnalysisTable;
    private final NetworkTableEntry mRunEntry;
    private final NetworkTableEntry mUpdateEntry;

    private final Collection<Consumer<Boolean>> mRunListeners;
    private final int mRunListener;

    private volatile Thread mLastThread;

    public RemoveVisionClient(NetworkTable analysisTable, NetworkTableEntry runEntry, NetworkTableEntry updateEntry) {
        mAnalysisTable = analysisTable;
        mRunEntry = runEntry;
        mUpdateEntry = updateEntry;

        mRunListeners = new ArrayList<>();
        mRunListener = runEntry.addListener(this::onRunUpdate, EntryListenerFlags.kUpdate);

        mLastThread = null;
    }

    public RemoveVisionClient(NetworkTable parentTable) {
        this(parentTable.getSubTable("analysis"),
                parentTable.getEntry("run"),
                parentTable.getEntry("update"));
    }

    public boolean shouldRun() {
        return mRunEntry.getBoolean(false);
    }

    public void addRunListener(Consumer<Boolean> listener) {
        synchronized (mRunListeners) {
            boolean first = shouldRun();
            mRunListeners.add(listener);
            boolean last = shouldRun();

            if (first == last) {
                listener.accept(first);
            }
        }
    }

    public void newAnalysis(Analysis analysis) {
        cancelUpdateThread();

        if (mUpdateEntry.getBoolean(false)) {
            // So this is problematic, because it means that the robot has not finished
            // updating itself from the last analysis. In that case, we can:
            // - wait a bit and try again or
            // - listen to when mUpdateEntry is false.
            // The latter is difficult because there is no way to ensure atomicity, and
            // thus we would go with the former.
            // The former may be a bit resource intensive, but it's the best we can do.
            delayedUpdate(analysis);
        } else {
            updateAnalysis(analysis);
        }
    }

    private void onRunUpdate(EntryNotification notification) {
        synchronized (mRunListeners) {
            for (Consumer<Boolean> listener : mRunListeners) {
                listener.accept(notification.value.getBoolean());
            }
        }
    }

    private void delayedUpdate(Analysis analysis) {
        Thread updateThread = new Thread(()-> {
            while (!Thread.interrupted()) {
                if (mUpdateEntry.getBoolean(false)) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                updateAnalysis(analysis);
            }
        }, "remote-update-flag");

        updateThread.setDaemon(true);
        mLastThread = updateThread;
        updateThread.start();
    }

    private void cancelUpdateThread() {
        Thread thread = mLastThread;
        mLastThread = null;

        if (thread != null) {
            thread.interrupt();
        }
    }

    private void updateAnalysis(Analysis analysis) {
        for (Map.Entry<String, Object> entry : analysis.getData().entrySet()) {
            NetworkTableEntry tableEntry = mAnalysisTable.getEntry(entry.getKey());
            tableEntry.setValue(entry.getValue());
        }

        mUpdateEntry.setBoolean(true);
    }

    @Override
    public void close() {
        cancelUpdateThread();
        mRunEntry.removeListener(mRunListener);
    }
}
