package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.processing.analysis.Analysis;

public class AnalysisResult {

    private final Analysis mAnalysis;
    private final Time mTimestamp;

    public AnalysisResult(Analysis analysis, Time timestamp) {
        mAnalysis = analysis;
        mTimestamp = timestamp;
    }

    public Analysis getAnalysis() {
        return mAnalysis;
    }

    public Time getTimestamp() {
        return mTimestamp;
    }
}
