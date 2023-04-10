package com.flash3388.frc.nt.obsr;

import com.flash3388.flashlib.net.obsr.ObjectStorage;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.util.unique.InstanceId;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NtObjectStorage implements ObjectStorage {

    private final NetworkTable mRootTable;
    private final NetworkTable mRootInstanceTable;

    public NtObjectStorage(NetworkTableInstance instance, InstanceId ourId) {
        mRootTable = instance.getTable("FlashLib");
        mRootInstanceTable = mRootTable.getSubTable("instances").getSubTable(ourId.toString());
    }


    @Override
    public StoredObject getRoot() {
        return new NtStoredObject(mRootTable);
    }

    @Override
    public StoredObject getInstanceRoot() {
        return new NtStoredObject(mRootInstanceTable);
    }
}
