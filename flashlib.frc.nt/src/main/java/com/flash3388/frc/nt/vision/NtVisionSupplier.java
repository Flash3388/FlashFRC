package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.processing.analysis.Analysis;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.Optional;

public class NtVisionSupplier {

    private final Clock mClock;
    private final NetworkTable mAnalysisTable;
    private final NetworkTableEntry mUpdateEntry;

    private volatile AnalysisResult mLatestResult;

    public NtVisionSupplier(Clock clock, NetworkTable analysisTable, NetworkTableEntry updateEntry) {
        mClock = clock;
        mAnalysisTable = analysisTable;
        mUpdateEntry = updateEntry;

        mUpdateEntry.setBoolean(false);
        mUpdateEntry.addListener(this::onUpdateEntryChange, EntryListenerFlags.kUpdate);

        mLatestResult = null;
    }

    public Optional<AnalysisResult> getLatestResult() {
        return Optional.ofNullable(mLatestResult);
    }

    public Optional<AnalysisResult> getLatestResult(Time maxTimePassed) {
        AnalysisResult result = mLatestResult;
        if (result == null) {
            return Optional.empty();
        }

        Time now = mClock.currentTime();
        Time passed = now.sub(result.getTimestamp());
        if (passed.after(maxTimePassed)) {
            return Optional.empty();
        }

        return Optional.of(result);
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

        mLatestResult = new AnalysisResult(builder.build(), mClock.currentTime());

        mUpdateEntry.setBoolean(false);
    }
}
