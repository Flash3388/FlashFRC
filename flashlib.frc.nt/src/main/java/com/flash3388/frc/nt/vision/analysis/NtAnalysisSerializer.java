package com.flash3388.frc.nt.vision.analysis;

import com.flash3388.flashlib.vision.analysis.Analysis;
import edu.wpi.first.networktables.NetworkTable;

import java.io.IOException;

public interface NtAnalysisSerializer {

    void serializeTo(NetworkTable table, Analysis analysis) throws IOException;
    Analysis deserializeFrom(NetworkTable table) throws IOException;
}
