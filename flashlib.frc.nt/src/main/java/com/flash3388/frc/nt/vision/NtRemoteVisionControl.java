package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.VisionResult;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.analysis.JsonAnalysis;
import com.flash3388.flashlib.vision.control.VisionControl;
import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.control.event.NewResultEvent;
import com.flash3388.flashlib.vision.control.event.VisionListener;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class NtRemoteVisionControl implements VisionControl {

    private final Clock mClock;
    private final NetworkTable mAnalysisTable;
    private final NetworkTable mOptionsTable;
    private final NetworkTableEntry mRunEntry;
    private final NetworkTableEntry mUpdateEntry;

    private final AtomicReference<VisionResult> mLatestResult;
    private final Collection<VisionListener> mListeners;
    private volatile boolean mIsRunning;
    private volatile int mUpdateListener;

    public NtRemoteVisionControl(Clock clock, NetworkTable analysisTable, NetworkTable optionsTable,
                                 NetworkTableEntry runEntry, NetworkTableEntry updateEntry) {
        mClock = clock;
        mAnalysisTable = analysisTable;
        mOptionsTable = optionsTable;
        mRunEntry = runEntry;
        mUpdateEntry = updateEntry;

        mIsRunning = false;
        mRunEntry.setBoolean(false);
        mUpdateEntry.setBoolean(false);

        mLatestResult = new AtomicReference<>();
        mListeners = new CopyOnWriteArrayList<>();
        mUpdateListener = -1;

        mRunEntry.addListener(this::onRunEntryChange, EntryListenerFlags.kUpdate);
    }

    public NtRemoteVisionControl(Clock clock, NetworkTable parentTable) {
        this(clock, parentTable.getSubTable("analysis"),
                parentTable.getSubTable("options"),
                parentTable.getEntry("run"), parentTable.getEntry("update"));
    }

    public NtRemoteVisionControl(Clock clock, String parentTableName) {
        this(clock, NetworkTableInstance.getDefault().getTable(parentTableName));
    }

    @Override
    public boolean isRunning() {
        return mUpdateListener > 0;
    }

    @Override
    public void start() {
        onStart();
        mRunEntry.setBoolean(true);
    }

    @Override
    public void stop() {
        onStop();
        mRunEntry.setBoolean(false);
    }

    @Override
    public <T> void setOption(VisionOption option, T value) {
        if (!option.valueType().isInstance(value)) {
            throw new IllegalArgumentException("Bad value for option. Doesn't match type");
        }

        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        entry.setValue(value);
    }

    @Override
    public <T> Optional<T> getOption(VisionOption option, Class<T> type) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        if (entry.exists()) {
            NetworkTableValue value = entry.getValue();
            return Optional.of(option.valueType().convertTo(value.getValue(), type));
        }

        return Optional.empty();
    }

    @Override
    public <T> T getOptionOrDefault(VisionOption option, Class<T> type, T defaultValue) {
        return getOption(option, type).orElse(defaultValue);
    }

    @Override
    public Optional<VisionResult> getLatestResult() {
        return getLatestResult(false);
    }

    @Override
    public Optional<VisionResult> getLatestResult(boolean clear) {
        return Optional.ofNullable(clear ? mLatestResult.getAndSet(null) : mLatestResult.get());
    }

    @Override
    public Optional<VisionResult> getLatestResult(Time maxTimestamp) {
        return getLatestResult(maxTimestamp, false);
    }

    @Override
    public Optional<VisionResult> getLatestResult(Time maxTimestamp, boolean clear) {
        VisionResult result = clear ? mLatestResult.getAndSet(null) : mLatestResult.get();
        if (result == null) {
            return Optional.empty();
        }

        Time now = mClock.currentTime();
        Time passed = now.sub(result.getTimestamp());
        if (passed.after(maxTimestamp)) {
            return Optional.empty();
        }

        return Optional.of(result);
    }

    @Override
    public void addListener(VisionListener listener) {
        mListeners.add(listener);
    }

    private synchronized void onStart() {
        if (mUpdateListener == -1) {
            mUpdateListener = mUpdateEntry.addListener(this::onUpdateEntryChange, EntryListenerFlags.kUpdate);
        }
        mUpdateEntry.setBoolean(false);

        mLatestResult.set(null);
        mIsRunning = true;
    }

    private synchronized void onStop() {
        mLatestResult.set(null);

        if (mUpdateListener != -1) {
            mUpdateEntry.removeListener(mUpdateListener);
            mUpdateListener = -1;
        }
        mIsRunning = false;
    }

    private void onRunEntryChange(EntryNotification notification) {
        boolean newValue = notification.value.getBoolean();
        if (newValue == mIsRunning) {
            return;
        }

        if (newValue) {
            onStart();
        } else {
            onStop();
        }
    }

    private void onUpdateEntryChange(EntryNotification notification) {
        if (!notification.value.getBoolean()) {
            return;
        }

        NetworkTableEntry entry = mAnalysisTable.getEntry("raw");
        Analysis analysis = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(entry.getRaw(new byte[0]));
             DataInputStream dataInputStream = new DataInputStream(inputStream);) {
            analysis = new JsonAnalysis(dataInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            mUpdateEntry.setBoolean(false);
            return;
        }

        VisionResult result = new VisionResult(analysis, mClock.currentTime());
        mLatestResult.set(result);

        NewResultEvent event = new NewResultEvent(result);
        mListeners.forEach((listener)-> listener.onNewResult(event));

        mUpdateEntry.setBoolean(false);
    }
}
