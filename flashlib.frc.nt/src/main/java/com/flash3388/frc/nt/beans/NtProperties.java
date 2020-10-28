package com.flash3388.frc.nt.beans;

import com.beans.BooleanProperty;
import com.beans.DoubleProperty;
import com.beans.IntProperty;
import com.beans.Property;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NtProperties {

    public static BooleanProperty newBooleanProperty(String tableName, String entryName) {
        return newBooleanProperty(NetworkTableInstance.getDefault(), tableName, entryName);
    }

    public static BooleanProperty newBooleanProperty(NetworkTableInstance instance, String tableName, String entryName) {
        return newBooleanProperty(instance.getTable(tableName), entryName);
    }

    public static BooleanProperty newBooleanProperty(NetworkTable table, String entryName) {
        return newBooleanProperty(table.getEntry(entryName));
    }

    public static BooleanProperty newBooleanProperty(NetworkTableEntry entry) {
        return new NtBooleanProperty(entry);
    }

    public static DoubleProperty newDoubleProperty(String tableName, String entryName) {
        return newDoubleProperty(NetworkTableInstance.getDefault(), tableName, entryName);
    }

    public static DoubleProperty newDoubleProperty(NetworkTableInstance instance, String tableName, String entryName) {
        return newDoubleProperty(instance.getTable(tableName), entryName);
    }

    public static DoubleProperty newDoubleProperty(NetworkTable table, String entryName) {
        return newDoubleProperty(table.getEntry(entryName));
    }

    public static DoubleProperty newDoubleProperty(NetworkTableEntry entry) {
        return new NtDoubleProperty(entry);
    }

    public static IntProperty newIntProperty(String tableName, String entryName) {
        return newIntProperty(NetworkTableInstance.getDefault(), tableName, entryName);
    }

    public static IntProperty newIntProperty(NetworkTableInstance instance, String tableName, String entryName) {
        return newIntProperty(instance.getTable(tableName), entryName);
    }

    public static IntProperty newIntProperty(NetworkTable table, String entryName) {
        return newIntProperty(table.getEntry(entryName));
    }

    public static IntProperty newIntProperty(NetworkTableEntry entry) {
        return new NtIntProperty(entry);
    }

    public static Property<String> newStringProperty(String tableName, String entryName) {
        return newStringProperty(NetworkTableInstance.getDefault(), tableName, entryName);
    }

    public static Property<String> newStringProperty(NetworkTableInstance instance, String tableName, String entryName) {
        return newStringProperty(instance.getTable(tableName), entryName);
    }

    public static Property<String> newStringProperty(NetworkTable table, String entryName) {
        return newStringProperty(table.getEntry(entryName));
    }

    public static Property<String> newStringProperty(NetworkTableEntry entry) {
        return new NtStringProperty(entry);
    }
}
