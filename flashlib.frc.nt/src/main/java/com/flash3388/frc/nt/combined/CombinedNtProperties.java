package com.flash3388.frc.nt.combined;

import edu.wpi.first.networktables.*;

import java.util.HashMap;
import java.util.Map;

public class CombinedNtProperties {
    private final Map<String, NetworkTableEntry> NtEntryMap;
    private final NetworkTable table;

    public CombinedNtProperties(String name) {
        this(NetworkTableInstance.getDefault(), name);
    }

    public CombinedNtProperties(NetworkTableInstance tableInstance, String name) {
        NtEntryMap = new HashMap<>();
        table = tableInstance.getTable(name);
    }

    public void addBooleanEntry(String name, boolean initialValue) {
        addEntry(name, NetworkTableValue.makeBoolean(initialValue));
    }

    public void addIntEntry(String name, int initialValue) {
        addDoubleEntry(name, initialValue);
    }

    public void addDoubleEntry(String name, double initialValue) {
        addEntry(name, NetworkTableValue.makeDouble(initialValue));
    }

    public void addStringEntry(String name, String initialValue) {
        addEntry(name, NetworkTableValue.makeString(initialValue));
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
        return NtEntryMap.get(name);
    }

    private void addEntry(String name, NetworkTableValue value) {
        NetworkTableEntry entry = table.getEntry(name);
        entry.setValue(value);
        NtEntryMap.put(name, entry);
    }

    private NetworkTableValue getEntryValue(String name) {
        return getEntry(name).getValue();
    }
}
