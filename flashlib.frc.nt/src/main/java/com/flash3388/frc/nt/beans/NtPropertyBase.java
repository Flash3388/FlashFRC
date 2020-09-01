package com.flash3388.frc.nt.beans;

import com.flash3388.frc.nt.NtEntryIncorrectTypeException;
import com.flash3388.frc.nt.NtEntryMissingException;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

class NtPropertyBase {

    private final NetworkTableEntry mEntry;

    public NtPropertyBase(NetworkTableEntry entry) {
        mEntry = entry;
    }

    protected NetworkTableValue getOfType(NetworkTableType wantedType) {
        if (!mEntry.exists()) {
            throw new NtEntryMissingException(mEntry);
        }
        if (mEntry.getType() != wantedType) {
            throw new NtEntryIncorrectTypeException(wantedType, mEntry.getType());
        }

        return mEntry.getValue();
    }

    protected void set(NetworkTableValue value) {
        if (mEntry.getType() != value.getType()) {
            throw new NtEntryIncorrectTypeException(value.getType(), mEntry.getType());
        }

        mEntry.setValue(value);
    }

    public NetworkTableEntry getEntry() {
        return mEntry;
    }
}
