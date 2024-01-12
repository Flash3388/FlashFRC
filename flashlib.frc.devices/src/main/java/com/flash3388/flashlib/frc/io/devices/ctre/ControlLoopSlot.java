package com.flash3388.flashlib.frc.io.devices.ctre;


public interface ControlLoopSlot {

    // TODO: SUPPORT MORE FUNCTIONALITY
    //      FOR PHOENIX 5: LOOP ERROR, IZONE, KF

    enum Slot {
        SLOT0(0),
        SLOT1(1),
        SLOT2(2);

        private final int mIndex;

        Slot(int index) {
            mIndex = index;
        }

        public int index() {
            return mIndex;
        }
    }

    interface ConfigurationEditor {

        /**
         * Sets the proportional gain.
         * @param kp gain
         */
        ConfigurationEditor setP(double value);

        /**
         * Sets the integral gain.
         * @param ki gain
         */
        ConfigurationEditor setI(double value);

        /**
         * Sets the derivative gain.
         * @param kd gain
         */
        ConfigurationEditor setD(double value);

        /**
         * Sets the feed-forward gain.
         * @param kf gain
         */
        ConfigurationEditor setF(double value);

        /**
         * Sets the integral zone, which configures resetting of the integral
         * accumulator when the error is bigger then this zone.
         *
         * @param iZone zone, in sensor units.
         */
        ConfigurationEditor setIZone(double value);

        /**
         * Sets the max value of the integral accumulator.
         * @param max max integral value, in sensor units.
         */
        ConfigurationEditor setMaxIAccumulator(double value);

        /**
         * Sets the max allowable error.
         * @param error error, in sensor units.
         */
        ConfigurationEditor setAllowableError(double value);

        /**
         * Sets the maximum output from the pid loop.
         * @param output output as percent [0, 1]
         */
        ConfigurationEditor setPeakOutput(double value);

        void save();
    }

    ConfigurationEditor configure();

    /**
     * Gets the last error.
     * @return error, in sensor units.
     */
    double getError();
}
