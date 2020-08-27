package com.flash3388.frc.nt.combined;

import edu.wpi.first.networktables.*;

import java.util.HashMap;
import java.util.Map;

public class NtTable {

    private final NetworkTable mTable;

    public NtTable(String name) {
        this(NetworkTableInstance.getDefault(), name);
    }

    public NtTable(NetworkTableInstance tableInstance, String name) {
         this(tableInstance.getTable(name));
    }

    public NtTable(NetworkTable table) {
        mTable = table;
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
}
