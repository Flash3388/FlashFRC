package com.flash3388.frc.nt.vision;

import com.castle.reflect.DataType;
import com.flash3388.flashlib.vision.control.VisionOption;

public class StandardVisionOptions {

    private StandardVisionOptions() {}

    public static final VisionOption DEBUG = VisionOption.create("debug", new DataType.Primitive(Boolean.class));
    public static final VisionOption EXPOSURE = VisionOption.create("exposure", new DataType.Numeric());
}
