package com.flash3388.frc.nt.vision;

import com.castle.reflect.Types;
import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.analysis.Analysis;
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

    public <T> boolean hasOptionValue(VisionOption<T> option) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        return entry.exists();
    }

    public <T> T getOptionOrDefault(VisionOption<T> option, T defaultValue) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        NetworkTableValue value = entry.getValue();
        if (!entry.exists()) {
            return defaultValue;
        }

        return Types.smartCast(value.getValue(), option.valueType());
    }

    public <T> void addOptionListener(VisionOption<T> option, BiConsumer<VisionOption<? super T>, ? super T> valueConsumer) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        int listener = entry.addListener((notification)-> {
            T value = Types.smartCast(
                    notification.value.getValue(),
                    option.valueType());
            valueConsumer.accept(option, value);
        }, EntryListenerFlags.kUpdate);

        mOptionListeners.add(listener);
    }

    public <T> void setOption(VisionOption<T> option, T value) {
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
