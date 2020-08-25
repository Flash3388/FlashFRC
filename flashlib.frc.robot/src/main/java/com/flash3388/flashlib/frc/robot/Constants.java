package com.flash3388.flashlib.frc.robot;

import com.beans.BooleanProperty;
import com.beans.DoubleProperty;
import com.beans.IntProperty;
import com.beans.util.function.Suppliers;
import com.flash3388.frc.nt.beans.NtPropertyFactory;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

public class Constants {

    private static final String DEFAULT_TABLE_NAME = "constants";

    private Constants() {}

    public static BooleanSupplier newStatic(boolean value) {
        return Suppliers.of(value);
    }

    public static BooleanProperty newNt(String table, String name, boolean value) {
        BooleanProperty property = NtPropertyFactory.newBooleanProperty(table, name);
        property.setAsBoolean(value);
        return property;
    }

    public static BooleanProperty newNt(String name, boolean value) {
        return newNt(DEFAULT_TABLE_NAME, name, value);
    }

    public static DoubleSupplier newStatic(double value) {
        return Suppliers.of(value);
    }

    public static DoubleProperty newNt(String table, String name, double value) {
        DoubleProperty property = NtPropertyFactory.newDoubleProperty(table, name);
        property.setAsDouble(value);
        return property;
    }

    public static DoubleProperty newNt(String name, double value) {
        return newNt(DEFAULT_TABLE_NAME, name, value);
    }

    public static IntSupplier newStatic(int value) {
        return Suppliers.of(value);
    }

    public static IntProperty newNt(String table, String name, int value) {
        IntProperty property = NtPropertyFactory.newIntProperty(table, name);
        property.setAsInt(value);
        return property;
    }

    public static IntProperty newNt(String name, int value) {
        return newNt(DEFAULT_TABLE_NAME, name, value);
    }
}
