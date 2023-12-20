package com.flash3388.flashlib.frc.io.devices;

public interface CTREPidController {

    /**
     * Gets the last error.
     * @return error, in sensor units.
     */
    double getError();

    /**
     * Sets the proportional gain.
     * @param kp gain
     */
    void setP(double kp);

    /**
     * Sets the integral gain.
     * @param ki gain
     */
    void setI(double ki);

    /**
     * Sets the derivative gain.
     * @param kd gain
     */
    void setD(double kd);

    /**
     * Sets the feed-forward gain.
     * @param kf gain
     */
    void setF(double kf);

    /**
     * Sets the integral zone, which configures resetting of the integral
     * accumulator when the error is bigger then this zone.
     *
     * @param iZone zone, in sensor units.
     */
    void setIZone(double iZone);

    /**
     * Sets the max value of the integral accumulator.
     * @param max max integral value, in sensor units.
     */
    void setMaxIAccumulator(double max);

    /**
     * Sets the max allowable error.
     * @param error error, in sensor units.
     */
    void setAllowableError(double error);

    /**
     * Sets the maximum output from the pid loop.
     * @param output output as percent [0, 1]
     */
    void setPeakOutput(double output);
}
