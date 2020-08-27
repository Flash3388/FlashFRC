package com.flash3388.frc.nt.beans;

import com.beans.DoubleProperty;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

import java.util.Objects;

public class NtDoubleProperty extends NtPropertyBase implements DoubleProperty {

    public NtDoubleProperty(NetworkTableEntry entry) {
        super(entry);
    }

    @Override
    public double getAsDouble() {
        NetworkTableValue value = getOfType(NetworkTableType.kDouble);
        return value.getDouble();
    }

    @Override
    public void setAsDouble(double value) {
        set(NetworkTableValue.makeDouble(value));
    }

    @Override
    public Double get() {
        return getAsDouble();
    }

    @Override
    public void set(Double value) {
        Objects.requireNonNull(value, "property value cannot be null");
        setAsDouble(value);
    }
}
