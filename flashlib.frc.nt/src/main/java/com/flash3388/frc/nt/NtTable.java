package com.flash3388.frc.nt;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

public class NtTable {

    private final NetworkTable mTable;

    public NtTable(NetworkTable table) {
        mTable = table;
    }

    public NtTable(String name) {
        this(NetworkTableInstance.getDefault(), name);
    }

    public NtTable(NetworkTableInstance tableInstance, String name) {
         this(tableInstance.getTable(name));
    }

    public boolean getBooleanEntryValue(String name) {
        return getEntryValue(name).getBoolean();
    }

    public int getIntEntryValue(String name) {
        return (int) getEntryValue(name).getDouble();
    }

    public double getDoubleEntryValue(String name) {
        return getEntryValue(name).getDouble();
    }

    public String getStringEntryValue(String name) {
        return getEntryValue(name).getString();
    }

    public NetworkTableEntry getEntry(String name) {
        return mTable.getEntry(name);
    }

    private NetworkTableValue getEntryValue(String name) {
        return getEntry(name).getValue();
    }

    public class Builder {

        private final NetworkTable mTable;

        public Builder(NetworkTable table) {
            mTable = table;
        }

        public Builder(String name) {
            this(NetworkTableInstance.getDefault(), name);
        }

        public Builder(NetworkTableInstance tableInstance, String name) {
            this(tableInstance.getTable(name));
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
}
