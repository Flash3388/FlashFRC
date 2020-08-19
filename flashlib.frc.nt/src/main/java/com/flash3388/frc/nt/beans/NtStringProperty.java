package com.flash3388.frc.nt.beans;

import com.beans.Property;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

public class NtStringProperty extends NtPropertyBase implements Property<String> {

    public NtStringProperty(NetworkTableEntry entry) {
        super(entry);
    }

    @Override
    public String get() {
        NetworkTableValue value = getOfType(NetworkTableType.kString);
        return value.getString();
    }

    @Override
    public void set(String value) {
        set(NetworkTableValue.makeString(value));
    }
}
