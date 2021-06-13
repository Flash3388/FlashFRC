package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.VisionResult;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.control.VisionControl;
import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.control.event.NewResultEvent;
import com.flash3388.flashlib.vision.control.event.VisionListener;
import com.flash3388.frc.nt.vision.analysis.NtAnalysisSerializer;
import com.flash3388.frc.nt.vision.analysis.SingleEntryNtAnalysisSerializer;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class NtRemoteVisionControl implements VisionControl {

    private final Clock mClock;
    private final NetworkTable mAnalysisTable;
    private final NetworkTable mOptionsTable;
    private final NetworkTableEntry mRunEntry;
    private final NetworkTableEntry mUpdateEntry;
    private final NtAnalysisSerializer mSerializer;

    private final AtomicReference<VisionResult> mLatestResult;
    private final Collection<VisionListener> mListeners;
    private volatile int mListener;

    public NtRemoteVisionControl(Clock clock, NetworkTable analysisTable, NetworkTable optionsTable,
                                 NetworkTableEntry runEntry,
                                 NetworkTableEntry updateEntry,
                                 NtAnalysisSerializer serializer) {
        mClock = clock;
        mAnalysisTable = analysisTable;
        mOptionsTable = optionsTable;
        mRunEntry = runEntry;
        mUpdateEntry = updateEntry;
        mSerializer = serializer;

        mRunEntry.setBoolean(false);
        mUpdateEntry.setBoolean(false);

        mLatestResult = new AtomicReference<>();
        mListeners = new CopyOnWriteArrayList<>();
        mListener = -1;
    }

    public NtRemoteVisionControl(Clock clock, NetworkTable parentTable) {
        this(clock, parentTable.getSubTable("analysis"),
                parentTable.getSubTable("options"),
                parentTable.getEntry("run"),
                parentTable.getEntry("update"),
                new SingleEntryNtAnalysisSerializer());
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
        mRunEntry.setBoolean(true);
    }

    @Override
    public void stop() {
        mRunEntry.setBoolean(false);
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
    public <T> Optional<T> getOption(VisionOption<T> option) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        if (entry.exists()) {
            NetworkTableValue value = entry.getValue();
            return Optional.of(option.valueType().cast(value.getValue()));
        }

        return Optional.empty();
    }

    @Override
    public <T> T getOptionOrDefault(VisionOption<T> option, T defaultValue) {
        return getOption(option).orElse(defaultValue);
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

    private void onUpdateEntryChange(EntryNotification notification) {
        if (!notification.value.getBoolean()) {
            return;
        }

        try {
            Analysis analysis = mSerializer.deserializeFrom(mAnalysisTable);
            VisionResult result = new VisionResult(analysis, mClock.currentTime());
            mLatestResult.set(result);

            NewResultEvent event = new NewResultEvent(result);
            mListeners.forEach((listener)-> listener.onNewResult(event));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mUpdateEntry.setBoolean(false);
    }
}
