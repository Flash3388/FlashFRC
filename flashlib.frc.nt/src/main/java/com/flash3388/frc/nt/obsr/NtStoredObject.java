package com.flash3388.frc.nt.obsr;

import com.beans.observables.RegisteredListener;
import com.flash3388.flashlib.net.obsr.EntryListener;
import com.flash3388.flashlib.net.obsr.EntryModificationEvent;
import com.flash3388.flashlib.net.obsr.StoredEntry;
import com.flash3388.flashlib.net.obsr.StoredObject;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;

import java.util.EnumSet;

public class NtStoredObject implements StoredObject {

    private final NetworkTable mTable;

    public NtStoredObject(NetworkTable table) {
        mTable = table;
    }

    @Override
    public StoredObject getChild(String name) {
        return new NtStoredObject(mTable.getSubTable(name));
    }

    @Override
    public StoredEntry getEntry(String name) {
        return new NtStoredEntry(mTable.getEntry(name));
    }

    @Override
    public void delete() {
        for (String key : mTable.getKeys()) {
            NetworkTableEntry entry = mTable.getEntry(key);
            entry.unpublish();
        }

        for (String key : mTable.getSubTables()) {
            NetworkTable table = mTable.getSubTable(key);
            new NtStoredObject(table).delete();
        }
    }

    @Override
    public RegisteredListener addListener(EntryListener listener) {
        NetworkTable.TableEventListener innerListener = new NetworkTable.TableEventListener() {
            @Override
            public void accept(NetworkTable table, String key, NetworkTableEvent event) {
                listener.onEntryModification(new EntryModificationEvent(
                        new NtStoredEntry(table.getEntry(key)),
                        table.getPath().concat("/").concat(key),
                        Helper.ntValueToObsr(event.valueData.value),
                        Helper.modificationTypeFromEventKind(event)
                ));
            }
        };

        int handle = mTable.addListener(
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                innerListener
        );

        return ()-> {
            mTable.removeListener(handle);
        };
    }
}
