package com.flash3388.frc.nt;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

public class NtTable {

    public class Builder {

        private final NetworkTable mTable;

        public Builder(NetworkTable table) {
            mTable = table;
        }

        public Builder(NetworkTableInstance tableInstance, String name) {
            this(tableInstance.getTable(name));
        }

        public Builder(String name) {
            this(NetworkTableInstance.getDefault(), name);
        }

        public Builder addBooleanEntry(String name, boolean initialValue) {
            return addEntry(name, NetworkTableValue.makeBoolean(initialValue));
        }

        public Builder addIntEntry(String name, int initialValue) {
            return addDoubleEntry(name, initialValue);
        }

        public Builder addDoubleEntry(String name, double initialValue) {
            return addEntry(name, NetworkTableValue.makeDouble(initialValue));
        }

        public Builder addStringEntry(String name, String initialValue) {
            return addEntry(name, NetworkTableValue.makeString(initialValue));
        }

        public Builder addEntry(String name, NetworkTableValue value) {
            NetworkTableEntry entry = mTable.getEntry(name);
            entry.setValue(value);

            return this;
        }

        public NtTable build() {
            return new NtTable(mTable);
        }
    }

    private final NetworkTable mTable;

    public NtTable(NetworkTable table) {
        mTable = table;
    }

    public NtTable(NetworkTableInstance tableInstance, String name) {
        this(tableInstance.getTable(name));
    }

    public NtTable(String name) {
        this(NetworkTableInstance.getDefault(), name);
    }

    public boolean getAsBoolean(String entryName) {
        return getEntryValue(entryName).getBoolean();
    }

    public int getAsInt(String entryName) {
        return (int) getEntryValue(entryName).getDouble();
    }

    public double getAsDouble(String entryName) {
        return getEntryValue(entryName).getDouble();
    }

    public String getAsString(String entryName) {
        return getEntryValue(entryName).getString();
    }

    public NetworkTableEntry getEntry(String name) {
        return mTable.getEntry(name);
    }

    private NetworkTableValue getEntryValue(String name) {
        return getEntry(name).getValue();
    }
}
