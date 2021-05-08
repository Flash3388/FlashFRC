package com.flash3388.frc.nt.vision;

import com.flash3388.flashlib.vision.control.VisionOption;

public class StandardVisionOptions {

    private StandardVisionOptions() {}

    public static final VisionOption<Boolean> DEBUG = VisionOption.create("debug", Boolean.class);
    public static final VisionOption<Integer> EXPOSURE = VisionOption.create("exposure", Integer.class);
}
