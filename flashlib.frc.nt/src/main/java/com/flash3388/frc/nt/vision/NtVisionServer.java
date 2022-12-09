package com.flash3388.frc.nt.vision;

import com.castle.reflect.NumberAdapter;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.control.VisionOption;
import edu.wpi.first.networktables.GenericSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NtVisionServer implements Closeable {

    private final NetworkTable mOptionsTable;
    private final RemoveVisionClient mRemoveVisionClient;

    private final Collection<RegisterListener> mOptionListeners;

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

        return smartCast(value.getValue(), option.valueType());
    }

    public <T> void addOptionListener(VisionOption<T> option, BiConsumer<VisionOption<? super T>, ? super T> valueConsumer) {
        NetworkTableEntry entry = mOptionsTable.getEntry(option.name());
        GenericSubscriber subscriber = entry.getTopic().genericSubscribe();
        int listener = NetworkTableInstance.getDefault().addListener(
                subscriber,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                (event)-> {
            T value = smartCast(
                    event.valueData.value.getValue(),
                    option.valueType());
            valueConsumer.accept(option, value);
        });


        mOptionListeners.add(new RegisterListener(subscriber, listener));
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
        for (RegisterListener listener : mOptionListeners) {
            listener.close();
        }
        mOptionListeners.clear();

        mRemoveVisionClient.close();
    }

    private static <T> T smartCast(Object value, Class<T> type) {
        if (type.isAssignableFrom(value.getClass())) {
            return type.cast(value);
        }

        if (value instanceof Number && type.getSuperclass().equals(Number.class)) {
            return type.cast(new NumberAdapter().adapt((Number) value, type));
        }

        throw new AssertionError("Can't cast " + value.getClass() + "to " + type);
    }

    private static class RegisterListener {
        GenericSubscriber subscriber;
        int listener;

        RegisterListener(GenericSubscriber subscriber, int listener) {
            this.subscriber = subscriber;
            this.listener = listener;
        }

        public void close() {
            NetworkTableInstance.getDefault().removeListener(listener);
            subscriber.close();
        }
    }
}
