package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.VisionResult;
import com.flash3388.flashlib.vision.control.VisionControl;
import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.processing.analysis.Analysis;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NtRemoteVisionControl implements VisionControl {

    private final Clock mClock;
    private final NetworkTable mAnalysisTable;
    private final NetworkTable mOptionsTable;
    private final NetworkTableEntry mUpdateEntry;

    private final AtomicReference<VisionResult> mLatestResult;
    private final Collection<Consumer<VisionResult>> mListeners;
    private volatile int mListener;

    public NtRemoteVisionControl(Clock clock, NetworkTable analysisTable, NetworkTable optionsTable,
                                 NetworkTableEntry updateEntry) {
        mClock = clock;
        mAnalysisTable = analysisTable;
        mOptionsTable = optionsTable;
        mUpdateEntry = updateEntry;

        mLatestResult = new AtomicReference<>();
        mListeners = new CopyOnWriteArrayList<>();
        mListener = -1;
    }

    public NtRemoteVisionControl(Clock clock, NetworkTable parentTable) {
        this(clock, parentTable.getSubTable("analysis"),
                parentTable.getSubTable("options"),
                parentTable.getEntry("update"));
    }

    public NtRemoteVisionControl(Clock clock, String parentTableName) {
        this(clock, NetworkTableInstance.getDefault().getTable(parentTableName));
    }

    @Override
    public boolean isRunning() {
        return mListener > 0;
    }

    @Override
    public void start() {
        mLatestResult.set(null);
        mUpdateEntry.setBoolean(false);
        mListener = mUpdateEntry.addListener(this::onUpdateEntryChange, EntryListenerFlags.kUpdate);
    }

    @Override
    public void stop() {
        mUpdateEntry.removeListener(mListener);
        mListener = -1;
        mLatestResult.set(null);
    }

    @Override
    public <T> void setOption(VisionOption<T> option, T value) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        entry.setValue(value);
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
    public void addResultListener(Consumer<VisionResult> consumer) {
        mListeners.add(consumer);
    }

    private void onUpdateEntryChange(EntryNotification notification) {
        if (!notification.value.getBoolean()) {
            return;
        }

        Analysis.Builder builder = new Analysis.Builder();
        for (String key : mAnalysisTable.getKeys()) {
            NetworkTableEntry entry = mAnalysisTable.getEntry(key);
            builder.put(key, entry.getValue().getValue());
        }

        VisionResult result = new VisionResult(builder.build(), mClock.currentTime());
        mLatestResult.set(result);
        mListeners.forEach((listener)-> listener.accept(result));

        mUpdateEntry.setBoolean(false);
    }
}
