package com.flash3388.frc.nt.beans;

import com.beans.IntProperty;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

import java.util.Objects;

public class NtIntProperty extends NtPropertyBase implements IntProperty {

    public NtIntProperty(NetworkTableEntry entry) {
        super(entry);
    }

    @Override
    public int getAsInt() {
        NetworkTableValue value = getOfType(NetworkTableType.kDouble);
        return (int) value.getDouble();
    }

    @Override
    public void setAsInt(int value) {
        set(NetworkTableValue.makeDouble(value));
    }

    @Override
    public Integer get() {
        return getAsInt();
    }

    @Override
    public void set(Integer value) {
        Objects.requireNonNull(value, "property value cannot be null");
        setAsInt(value);
    }
}
