package com.flash3388.flashlib.frc.io.devices;

public class CTRESensors {

    private CTRESensors() {}

    public static final int TALONFX_PPR = 2048;
    public static final int SRX_ENCODER_PPR = 4096;
    public static final int CANCODER_PPR = 4096;

    // gear ratio format = driver / driven

    public static double degreesToRawSensorUnits(double degrees, int ppr, double gearRatio) {
        return degrees / 360.0 * ppr * gearRatio;
    }

    public static double rawSensorUnitsToDegrees(double raw, int ppr, double gearRatio) {
        return raw * 360.0 / (gearRatio * ppr);
    }

    public static double rpmToRawSensorUnits(double rpm, int ppr, double gearRatio) {
        return rpm / 600.0 * ppr * gearRatio;
    }

    public static double rawSensorUnitsToRpm(double raw, int ppr, double gearRatio) {
        return (raw * (600.0 / ppr)) / gearRatio;
    }

    public static double rawSensorUnitsToDegreesPerSecond(double raw, int ppr, double gearRatio) {
        return (raw * (36.0 / ppr)) / gearRatio;
    }

    public static double degreesPerSecondToRawSensorUnits(double degreesPerSecond, int ppr, double gearRatio) {
        return degreesPerSecond / 36.0 * gearRatio * ppr;
    }

    public static double rawSensorUnitsToMetersPerSecond(double raw, int ppr, double gearRatio, double wheelRadius) {
        return rawSensorUnitsToRpm(raw, ppr, gearRatio) * (2 * wheelRadius * Math.PI) / 60.0;
    }

    public static double metersPerSecondToRawSensorUnits(double metersPerSecond, int ppr, double gearRatio, double wheelRadius) {
        return rpmToRawSensorUnits((metersPerSecond * 60) / (2 * wheelRadius * Math.PI), ppr, gearRatio);
    }

    public static double rawSensorUnitsToMeters(double raw, int ppr, double gearRatio, double wheelRadius) {
        return raw * ((2 * wheelRadius * Math.PI) / (gearRatio * ppr));
    }

    public static double metersToRawSensorUnits(double meters, int ppr, double gearRatio, double wheelRadius) {
        return meters / (2 * wheelRadius * Math.PI) * gearRatio * ppr;
    }
}
