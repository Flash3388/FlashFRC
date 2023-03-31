package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.io.RoboRioChannel;
import com.flash3388.flashlib.hid.HidChannel;
import com.flash3388.flashlib.hid.generic.GenericHidChannel;
import com.flash3388.flashlib.io.IoChannel;

/**
 * An hardware access factory for <em>RoboRIO</em> platforms.
 *
 * @since FlashFrc 2021.1.0
 */
public class RoboRio {

    private RoboRio() {}

    /**
     * Creates a new {@link IoChannel} for <em>RoboRIO</em> hardware access to be used with
     * {@link com.flash3388.flashlib.io.IoInterface} to create hardware access ports.
     * <pre>
     *     IoChannel channel = RoboRio.newIoChannel(1);
     *     DigitalInput input = robotControl.getIoInterface().newDigitalInput(channel);
     * </pre>
     * <p>
     *     There is no distinct indication that differentiates between types of ports in {@link IoChannel}.
     *     Thus, for both {@link com.flash3388.flashlib.io.DigitalInput} and {@link com.flash3388.flashlib.io.Pwm}
     *     channels numbered 1, the same {@link IoChannel} is used.
     * </p>
     *
     * @param channel channel number, as seen on the board.
     *
     * @return {@link IoChannel} representing the port.
     */
    public static IoChannel newIoChannel(int channel) {
        return new RoboRioChannel(channel);
    }

    /**
     * Creates a new {@link HidChannel} for <em>DriverStation</em> HID access to be used with
     * {@link com.flash3388.flashlib.hid.HidInterface} to create HID controllers.
     * <pre>
     *     HidChannel channel = RoboRio.newHidChannel(1);
     *     Joystick joystick = robotControl.getHidInterface().newJoystick(channel);
     * </pre>
     *
     * @param channel channel number, as seen on the driver station.
     *
     * @return {@link HidChannel} representing the port.
     */
    public static HidChannel newHidChannel(int channel) {
        return new GenericHidChannel(channel);
    }
}
