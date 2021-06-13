package com.flash3388.frc.nt.vision.analysis;

import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.analysis.AnalysisSerializer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SingleEntryNtAnalysisSerializer implements NtAnalysisSerializer {

    private final AnalysisSerializer mSerializer;

    public SingleEntryNtAnalysisSerializer() {
        mSerializer = new AnalysisSerializer();
    }

    @Override
    public void serializeTo(NetworkTable table, Analysis analysis) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            mSerializer.serializeTo(dataOutputStream, analysis);
            dataOutputStream.flush();
        }

        NetworkTableEntry entry = table.getEntry("data");
        entry.setRaw(outputStream.toByteArray());
    }

    @Override
    public Analysis deserializeFrom(NetworkTable table) throws IOException {
        NetworkTableEntry entry = table.getEntry("data");
        byte[] raw = entry.getRaw(null);
        if (raw == null) {
            throw new IOException("no value");
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(raw);
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            return mSerializer.deserializeFrom(dataInputStream);
        }
    }
}
