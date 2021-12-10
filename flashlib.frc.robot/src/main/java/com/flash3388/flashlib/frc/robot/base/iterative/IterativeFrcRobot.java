package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.robot.RobotInitializationException;

/**
 * Defines a specialized robot base based on a looping and modes model.
 * Control is separated into control modes, as defined by {@link FrcRobotControl#getMode()}.
 * Based on the mode, the <em>init</em> method for the mode is called once when entering from another
 * mode, and then <em>periodic</em> is called periodically, defined by the specific implementation
 * used.
 * <ul>
 *     <li>When the mode is {@link FrcRobotMode#DISABLED}, {@link #disabledInit()} and {@link #disabledPeriodic()}</li>
 *     <li>When the mode is {@link FrcRobotMode#OPERATOR_CONTROL}, {@link #teleopInit()} and {@link #teleopPeriodic()}</li>
 *     <li>When the mode is {@link FrcRobotMode#AUTONOMOUS}, {@link #autonomousInit()} and {@link #autonomousPeriodic()}</li>
 *     <li>When the mode is {@link FrcRobotMode#TEST}, {@link #testInit()} and {@link #testPeriodic()}</li>
 * </ul>
 * <p>
 *     {@link #robotPeriodic()} and {@link #robotStop()} are both independent from modes. The former is called
 *     after the <em>periodic</em> of any of the modes and the latter is called when the robot stops running.
 * </p>
 *
 * @since FlashFrc 2020.1.0
 *
 * @see LoopingRobotBase
 */
public interface IterativeFrcRobot {

    /**
     * <p>
     *      Initializer for creating {@link IterativeFrcRobot} robot from {@link FrcRobotControl}.
     *      This is generally where the implementing {@link IterativeFrcRobot} should
     *      perform initializing to robot control and systems.
     * </p>
     * <p>
     *     Generally, it is recommended to utilize a constructor for the implementation,
     *     so that the initialization of the robot is done in the constructor of the
     *     robot class.
     * </p>
     * <pre>
     *     class Robot implements IterativeFrcRobot {
     *
     *         private final FrcRobotControl robotControl;
     *
     *         public Robot(FrcRobotControl robotControl) {
     *              this.robotControl = robotControl;
     *         }
     *     }
     * </pre>
     * <p>
     *     Thus, it is possible to create an initializer using
     *     a simple method reference:
     * </p>
     * <pre>
     *     IterativeFrcRobot.Initializer initializer = Robot::new
     * </pre>
     *
     * @since FlashFrc 2020.1.0
     */
    @FunctionalInterface
    interface Initializer {
        IterativeFrcRobot init(FrcRobotControl robotControl) throws RobotInitializationException;
    }

    /**
     * Called when entering a mode with {@link FrcRobotMode#DISABLED}.
     * Use this to prepare for the control mode change.
     */
    void disabledInit();

    /**
     * Called periodically while in a mode with {@link FrcRobotMode#DISABLED}.
     * Implement a periodic logic for the control mode in here.
     *
     * <p>
     *     Being in <b>DISABLED</b> mode, this method is usually best left empty, as the robot
     *     should not be functioning in this control mode.
     * </p>
     */
    void disabledPeriodic();

    /**
     * Called when entering a mode with {@link FrcRobotMode#OPERATOR_CONTROL}.
     * Use this to prepare for the control mode change.
     */
    void teleopInit();

    /**
     * Called periodically while in a mode with {@link FrcRobotMode#OPERATOR_CONTROL}.
     * Implement a periodic logic for the control mode in here.
     *
     * <p>
     *     Being in <b>TELEOP</b> mode, this method is usually should implement controller-based
     *     logic, to operate the robot.
     * </p>
     */
    void teleopPeriodic();

    /**
     * Called when entering a mode with {@link FrcRobotMode#AUTONOMOUS}.
     * Use this to prepare for the control mode change.
     */
    void autonomousInit();

    /**
     * Called periodically while in a mode with {@link FrcRobotMode#AUTONOMOUS}.
     * Implement a periodic logic for the control mode in here.
     *
     * <p>
     *     Being in <b>AUTONOMOUS</b> mode, this method is usually should implement autonomous
     *     robot operation, and ignore any operator/user interaction.
     * </p>
     */
    void autonomousPeriodic();

    /**
     * Called when entering a mode with {@link FrcRobotMode#TEST}.
     * Use this to prepare for the control mode change.
     */
    void testInit();

    /**
     * Called periodically while in a mode with {@link FrcRobotMode#TEST}.
     * Implement a periodic logic for the control mode in here.
     *
     * <p>
     *     Being in <b>TEST</b> mode, this method can be used to do anything really, as it is meant
     *     to run tests on the robot operations.
     * </p>
     */
    void testPeriodic();

    /**
     * Called periodically after the mode-specific operations.
     * Use this to perform mode-global periodic code, such as dashboard updates and such.
     * Motion and motor operations are not recommended.
     */
    void robotPeriodic();

    /**
     * Called when the robot is stopping. Perform de-initialization here.
     * <p>
     *     It is generally not recommended to rely on this method, as it
     *     is not guaranteed that it will be called.
     * </p>
     */
    void robotStop();

    /**
     * Called periodically when the robot is in a simulation.
     */
    default void simulationPeriodic() {}
}
