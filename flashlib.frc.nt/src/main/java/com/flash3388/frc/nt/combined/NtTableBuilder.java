package com.flash3388.frc.nt.combined;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

public class NtTableBuilder {

    private final NetworkTable mTable;

    public NtTableBuilder(String name) {
        this(NetworkTableInstance.getDefault(), name);
    }

    public NtTableBuilder(NetworkTableInstance tableInstance, String name) {
        this(tableInstance.getTable(name));
    }

    public NtTableBuilder(NetworkTable table) {
        mTable = table;
    }

    public NtTableBuilder addBooleanEntry(String name, boolean initialValue) {
        return addEntry(name, NetworkTableValue.makeBoolean(initialValue));
    }

    public NtTableBuilder addIntEntry(String name, int initialValue) {
        return addDoubleEntry(name, initialValue);
    }

    public NtTableBuilder addDoubleEntry(String name, double initialValue) {
        return addEntry(name, NetworkTableValue.makeDouble(initialValue));
    }

    public NtTableBuilder addStringEntry(String name, String initialValue) {
        return addEntry(name, NetworkTableValue.makeString(initialValue));
    }

    public NtTableBuilder addEntry(String name, NetworkTableValue value) {
        NetworkTableEntry entry = mTable.getEntry(name);
        entry.setValue(value);

        return this;
    }

    public NtTable build() {
        return new NtTable(mTable);
    }
}
