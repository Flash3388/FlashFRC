package com.flash3388.frc.nt;

import edu.wpi.first.networktables.NetworkTableType;

public class NtEntryIncorrectTypeException extends RuntimeException {

    public NtEntryIncorrectTypeException(NetworkTableType usedType, NetworkTableType actualType) {
        super(String.format("Tried to use %s, when entry is of type %s", usedType.name(), actualType.name()));
    }
}
