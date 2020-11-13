package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.processing.analysis.Analysis;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NtVisionServer implements Closeable {

    private final NetworkTable mOptionsTable;
    private final RemoveVisionClient mRemoveVisionClient;

    private final Collection<Integer> mOptionListeners;

    public NtVisionServer(NetworkTable optionsTable, RemoveVisionClient removeVisionClient) {
        mOptionsTable = optionsTable;
        mRemoveVisionClient = removeVisionClient;

        mOptionListeners = new ArrayList<>();
    }

    public NtVisionServer(NetworkTable parentTable) {
        this(parentTable.getSubTable("options"), new RemoveVisionClient(parentTable));
    }

    public NtVisionServer(String parentTableName) {
        this(NetworkTableInstance.getDefault().getTable(parentTableName));
    }

    public boolean shouldRun() {
        return mRemoveVisionClient.shouldRun();
    }

    public void addRunListener(Consumer<Boolean> listener) {
        mRemoveVisionClient.addRunListener(listener);
    }

    public <T> T getOptionOrDefault(VisionOption<T> option, T defaultValue) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        NetworkTableValue value = entry.getValue();
        if (value.getType() == NetworkTableType.kUnassigned) {
            return defaultValue;
        }

        return option.valueType().cast(value.getValue());
    }

    public <T> void addOptionListener(VisionOption<T> option, BiConsumer<VisionOption<? super T>, ? super T> valueConsumer) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        int listener = entry.addListener((notification)-> {
            T value = option.valueType().cast(notification.value.getValue());
            valueConsumer.accept(option, value);
        }, EntryListenerFlags.kUpdate);

        mOptionListeners.add(listener);
    }

    public void newAnalysis(Analysis analysis) {
        mRemoveVisionClient.newAnalysis(analysis);
    }

    @Override
    public void close() {
        for (int listener : mOptionListeners) {
            mOptionsTable.removeEntryListener(listener);
        }

        mRemoveVisionClient.close();
    }
}
