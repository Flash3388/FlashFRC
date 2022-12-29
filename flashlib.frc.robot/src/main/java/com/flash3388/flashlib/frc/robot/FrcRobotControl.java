package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.robot.RobotControl;

/**
 * A specialized extension of {@link RobotControl} specifically for FRC robots.
 * Provides additional control and access components which only work with FRC robots.
 *
 * @since FlashFrc 2021.1.0
 */
public interface FrcRobotControl extends RobotControl {

    /**
     * Gets the {@link RobotFileSystem} object associated with the robot. This component
     * allows access to certain locations on the robot. For better abstraction and wider version support,
     * it is recommended to utilize this component when accessing paths on the file system from the robot code.
     * <pre>
     *     Path path = robotControl.getFileSystem().getPath("logs");
     * </pre>
     *
     * @return {@link RobotFileSystem} component.
     */
    RobotFileSystem getFileSystem();

    /**
     * Gets the current type of runtime for the robot code.
     *
     * @return runtime type.
     */
    RuntimeType getRuntimeType();

    /**
     * Gets whether the current runtime type is simulation, meaning
     * that the robot code is executing in a simulated environment,
     *
     * @return <b>true</b> if in simulation, <b>false</b> otherwise.
     * @see #getRuntimeType()
     */
    default boolean isRuntimeSimulation() {
        return getRuntimeType() == RuntimeType.SIMULATION;
    }
}
