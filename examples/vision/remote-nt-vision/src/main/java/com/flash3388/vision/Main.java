package com.flash3388.vision;

import com.flash3388.flashlib.vision.Pipeline;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.cv.CvCamera;
import com.flash3388.flashlib.vision.cv.CvImage;
import com.flash3388.flashlib.vision.processing.VisionPipeline;
import com.flash3388.frc.nt.vision.NtVisionServer;
import com.flash3388.frc.nt.vision.StandardVisionOptions;

import java.util.Optional;

public class Main {

    // This is the opposite side of robot-nt-vision example.
    // The code runs vision code which interacts and works with vision controls
    // and communicates over networktables.
    // There is no actual vision done, since the example is about how to use vision control.

    public static void main(String[] args) throws Exception {
        // NtVisionServer is a runner implementation to communicate with NtRemoteVisionControl
        // only it does not implement VisionControl since it's the other side.
        // With it we can listen to what the robot code wants, and send results.
        // Make sure the table name used, is the same as with the robot.
        // We use CvCamera from flashlib.vision.cv to access the camera.
        try (NtVisionServer server = new NtVisionServer("vision");
             CvCamera camera = new CvCamera(0)) {
            // Let's create the vision pipeline. More explained in there.
            Pipeline<CvImage> pipeline = createPipeline(server);
            // Our vision class will allow us to run the vision pipeline code.
            Vision vision = new Vision(camera, pipeline);

            // Now we need to listen to any requests from the user.
            // There are two types of requests: start/stop and options.

            // with this we can listen to changes in options and respond to them. This is one way we can
            // respond to options, but there is another demonstrated in createPipeline.
            server.addOptionListener(StandardVisionOptions.EXPOSURE, (option, value)-> {
                // in here we would need to modify the exposure setting of the camera.
                // but we won't actually do it.
            });

            // We must listen to start/stop setting. We can use a listener or use shouldRun.
            server.addRunListener((run) -> {
                if (run) {
                    // if the robot requested to run, we should start the vision code.
                   vision.start();
                } else {
                    // if the robot requested  to stop, we should stop the vision code.
                    vision.stop();
                }
            });

            // let's wait forever so that the main thread doesn't close.
            for(;;) {
                Thread.sleep(1000);
            }
        }
    }

    private static Pipeline<CvImage> createPipeline(NtVisionServer server) {
        // We need to create a pipeline for the vision.
        return new VisionPipeline.Builder<CvImage, Integer>()
                // We start the pipeline by processing the image. This include transformation, scaling
                // color changes and more.
                .process((image) -> {
                    // in here we will do the transformation.
                    // The generics used above specify the input and output types of the processor.
                    // image = CvImage, output = width of image.

                    // We can use OpenCv and more to do the processing.
                    // We can also access some of the options from the server and use it to modify our output.
                    boolean debug = server.getOptionOrDefault(StandardVisionOptions.DEBUG, false);
                    if (debug) {
                        // some debugging
                    }

                    return image.getWidth();
                })
                // Once the image is processed, we can analyse it for information to send to the robot.
                // This should produce an Analysis or empty depending on whether or not we could analyse the data.
                .analyse((image, postProcess) -> {
                    // image = original image sent to the processor
                    // postProcess = result from processor
                    // No we need to create an analysis based on those objects.
                    // The data placed in the analysis should be documented since it will be sent exactly to the robot.
                    Analysis analysis = Analysis.builder()
                            .put("offset", 4)
                            .build();
                    return Optional.of(analysis);
                })
                // If we managed to analyse, we need to send it to the server, so it would be sent to the robot,
                // and be retrieved with getLatestResult.
                .analysisTo(server::newAnalysis)
                // finish creating the pipeline.
                .build();
    }
}
