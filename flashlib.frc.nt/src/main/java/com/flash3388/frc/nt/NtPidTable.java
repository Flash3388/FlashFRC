package com.flash3388.frc.nt;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.scheduling.actions.Action;
import com.flash3388.frc.nt.beans.NtDoubleProperty;

import java.util.function.DoubleSupplier;

public class NtPidTable {

    private static final String PID_TABLE_NAME = "Pid";
    private static final String KP_ENTRY_NAME = "Kp";
    private static final String KI_ENTRY_NAME = "Ki";
    private static final String KD_ENTRY_NAME = "Kd";
    private static final String KF_ENTRY_NAME = "Kf";
    private static final String PID_INPUT_ENTRY_NAME = "Input";
    private final NtTable mTable;

    public NtPidTable(double kP, double kI, double kD, double kF, DoubleSupplier processVariableSupplier) {
        mTable = new NtTable.Builder(PID_TABLE_NAME)
                .addDoubleEntry(KP_ENTRY_NAME, 0)
                .addDoubleEntry(KI_ENTRY_NAME, 0)
                .addDoubleEntry(KD_ENTRY_NAME, 0)
                .addDoubleEntry(KD_ENTRY_NAME, 0)
                .addDoubleEntry(KF_ENTRY_NAME, 0)
                .addDoubleEntry(PID_INPUT_ENTRY_NAME, 0)
                .build();

        setPIDFValues(kP, kI, kD, kF);
        startProcessVariableUpdateAction(processVariableSupplier);
    }

    public static NtPidTable createWithP(double kP, DoubleSupplier processVariableSupplier) {
        return createWithPD(kP, 0, processVariableSupplier);
    }

    public static NtPidTable createWithPD(double kP, double kD, DoubleSupplier processVariableSupplier) {
        return createWithPID(kP, 0, kD, processVariableSupplier);
    }

    public static NtPidTable createWithPI(double kP, double kI, DoubleSupplier processVariableSupplier) {
        return createWithPID(kP, kI, 0, processVariableSupplier);
    }

    public static NtPidTable createWithPID(double kP, double kI, double kD, DoubleSupplier processVariableSupplier) {
        return new NtPidTable(kP, kI, kD, 0, processVariableSupplier);
    }

    public PidController createController() {
        return new PidController(
                new NtDoubleProperty(mTable.getEntry(KP_ENTRY_NAME)),
                new NtDoubleProperty(mTable.getEntry(KI_ENTRY_NAME)),
                new NtDoubleProperty(mTable.getEntry(KD_ENTRY_NAME)),
                new NtDoubleProperty(mTable.getEntry(KF_ENTRY_NAME)));
    }

    private void setPIDFValues(double kP, double kI, double kD, double kF) {
        setKp(kP);
        setKi(kI);
        setKd(kD);
        setKf(kF);
    }

    private void setKp(double value) {
        mTable.setAsDouble(KP_ENTRY_NAME, value);
    }

    private void setKi(double value) {
        mTable.setAsDouble(KI_ENTRY_NAME, value);
    }

    private void setKd(double value) {
        mTable.setAsDouble(KD_ENTRY_NAME, value);
    }

    private void setKf(double value) {
        mTable.setAsDouble(KF_ENTRY_NAME, value);
    }

    private void startProcessVariableUpdateAction(DoubleSupplier processVariableSupplier) {
        createProcessVariableUpdateAction(processVariableSupplier).start();
    }

    private Action createProcessVariableUpdateAction(DoubleSupplier processVariableSupplier) {
        return new ProcessVariableUpdateAction(processVariableSupplier, this::updateProcessVariable);
    }

    private void updateProcessVariable(double processVariable) {
        mTable.setAsDouble(PID_INPUT_ENTRY_NAME, processVariable);
    }
}
