package com.flash3388.frc.nt.beans;

import com.beans.BooleanProperty;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

import java.util.Objects;

public class NtBooleanProperty extends NtPropertyBase implements BooleanProperty {

    public NtBooleanProperty(NetworkTableEntry entry) {
        super(entry);
    }

    @Override
    public boolean getAsBoolean() {
        NetworkTableValue value = getOfType(NetworkTableType.kBoolean);
        return value.getBoolean();
    }

    @Override
    public void setAsBoolean(boolean value) {
        set(NetworkTableValue.makeBoolean(value));
    }

    @Override
    public Boolean get() {
        return getAsBoolean();
    }

    @Override
    public void set(Boolean value) {
        Objects.requireNonNull(value, "property value cannot be null");
        setAsBoolean(value);
    }
}
