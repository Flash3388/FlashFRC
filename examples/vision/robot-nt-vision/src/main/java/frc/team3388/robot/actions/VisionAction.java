package frc.team3388.robot.actions;

import com.flash3388.flashlib.scheduling.actions.ActionBase;
import com.flash3388.flashlib.vision.VisionResult;
import com.flash3388.flashlib.vision.control.VisionControl;
import com.flash3388.flashlib.vision.processing.analysis.Analysis;
import com.flash3388.frc.nt.vision.StandardVisionOptions;

import java.util.Optional;

public class VisionAction extends ActionBase {

    private final VisionControl mVisionControl;

    public VisionAction(VisionControl visionControl) {
        mVisionControl = visionControl;
    }

    @Override
    public void initialize() {
        // Before using and getting results from vision, we need to start
        // the visionControl. It is possible to leave the vision control operating continuously if wanted,
        // or to make it run only at specific times. This choice would depend on several things, but regardless
        // it's important to make sure vision control is running.
        mVisionControl.start();

        // We can set different options to vision control, which allow to configure the operation
        // of vision. It is possible that the actual code which is running the vision will completely ignore
        // the options, making them useless.
        // There are several built-in options, but it's easy to make custom ones.
        // The exposure option modifies the white exposure of the camera. It's an integer value.
        mVisionControl.setOption(StandardVisionOptions.EXPOSURE, 45);
    }

    @Override
    public void execute() {
        // Now that the vision is running and configured, we can query it for results.
        // However, the vision code could take some time to analyze results, and thus we may not
        // always have results. In addition, we might want to use each result only once,
        // which is way we clear the result using the parameter, which means an additional call to getLatestResult
        // without any update from the vision code will return an empty optional.
        // We can also get results based on how much time has passed since the result was received.
        // Take a look at the different overloads of getLatestResult
        Optional<VisionResult> optional = mVisionControl.getLatestResult(true);
        // We need to handle two cases now: we have a new result, or we don't.
        if (optional.isPresent()) {
            // if we have a new result, we can get an Analysis object from it.
            VisionResult result = optional.get();
            Analysis analysis = result.getAnalysis();

            // Now we can extract information from the Analysis object.
            // what information we receive/have depends on the vision code which creates the analysis,
            // so it's good to be aware of the vision code we are working with.
            double offset = analysis.get("offset", Double.class);

            // now we can do some algorithm with the robot.
        } else {
            // what we do in here depends on the type of algorithm we want. We can still operate the robot
            // based on the latest data, or we can do nothing, and more.
        }
    }

    @Override
    public boolean isFinished() {
        // in here we need to determine when to stop the action. This usually depends on the vision,
        // and is up to the specific situation.
        return false;
    }

    @Override
    public void end(boolean wasInterrupted) {
        // since we started the vision in initialize, we need to stop it here.
        // we can also modify the options as a de-initialization.
        mVisionControl.stop();
    }
}
