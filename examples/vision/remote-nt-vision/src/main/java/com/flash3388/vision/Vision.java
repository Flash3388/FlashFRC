package com.flash3388.vision;

import com.flash3388.flashlib.vision.Pipeline;
import com.flash3388.flashlib.vision.Source;
import com.flash3388.flashlib.vision.cv.CvImage;

public class Vision {

    private final Source<CvImage> mSource;
    private final Pipeline<CvImage> mPipeline;

    private Thread runningThread;

    public Vision(Source<CvImage> source, Pipeline<CvImage> pipeline) {
        mSource = source;
        mPipeline = pipeline;
    }

    public synchronized void start() {
        // We should use a thread to run the vision code since it needs to run
        // continuously and start is only called when there is a change in vision control.
        runningThread = new Thread(this::runVision, "vision-thread");
        runningThread.setDaemon(true);
        runningThread.start();
    }

    public synchronized void stop() {
        // We need to stop the vision code and dispose of the thread.
        if (runningThread != null) {
            runningThread.interrupt();
            runningThread = null;
        }
    }

    private void runVision() {
        // Run a continuous vision code loop which polls an image from the camera and
        // passes it to the pipeline for processing.
        while (!Thread.interrupted()) {
            try {
                CvImage image = mSource.get();
                mPipeline.process(image);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
