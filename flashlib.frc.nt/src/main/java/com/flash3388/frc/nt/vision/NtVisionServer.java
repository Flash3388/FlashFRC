package com.flash3388.frc.nt.vision;

import com.castle.reflect.Types;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.control.VisionOption;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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

    public NetworkTable getSubTable(String tableName) {
        return mRemoveVisionClient.getSubTable(tableName);
    }

    public boolean hasOptionValue(VisionOption option) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        return entry.exists();
    }

    public <T> T getOptionOrDefault(VisionOption option, Class<T> type, T defaultValue) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        NetworkTableValue value = entry.getValue();
        if (!entry.exists()) {
            return defaultValue;
        }

        return option.valueType().convertTo(value.getValue(), type);
    }

    public <T> void addOptionListener(VisionOption option, Class<T> type, BiConsumer<VisionOption, ? super T> valueConsumer) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        int listener = entry.addListener((notification)-> {
            T value = option.valueType().convertTo(notification.value.getValue(), type);
            valueConsumer.accept(option, value);
        }, EntryListenerFlags.kUpdate);

        mOptionListeners.add(listener);
    }

    public <T> void setOption(VisionOption option, T value) {
        if (!option.valueType().isInstance(value)) {
            throw new IllegalArgumentException("Bad value for option. Doesn't match type");
        }

        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        entry.setValue(value);
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
