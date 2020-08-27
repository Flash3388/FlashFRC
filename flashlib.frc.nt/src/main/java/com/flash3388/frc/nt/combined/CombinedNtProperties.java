package com.flash3388.frc.nt.combined;

import com.flash3388.frc.nt.beans.NtPropertyBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableType;

import java.util.HashMap;
import java.util.Map;

public class CombinedNtProperties {
    private final Map<String, NtPropertyBase> NtEntryMap;
    private final NetworkTable table;

    public CombinedNtProperties(String name) {
        this(NetworkTableInstance.getDefault(), name);
    }

    public CombinedNtProperties(NetworkTableInstance tableInstance, String name) {
        NtEntryMap = new HashMap<>();
        table = tableInstance.getTable(name);
    }

    private void addEntry(String name, NetworkTableType type) {
        NetworkTableEntry entry = table.getEntry(name);
        entry.setT
    }
}
