package com.flash3388.frc.nt.builtin;

import com.beans.observables.ObservableDoubleValue;
import com.beans.observables.ObservableFactory;
import com.beans.observables.listeners.ChangeEvent;
import com.beans.observables.listeners.ChangeListener;
import com.beans.observables.listeners.ListenerPredicate;
import com.beans.observables.listeners.ObservableEventController;
import com.beans.observables.properties.ObservableDoubleProperty;
import com.beans.observables.properties.SimpleObservableDoubleProperty;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.frc.nt.NtTable;
import com.flash3388.frc.nt.beans.NtDoubleProperty;

import java.util.Observer;

public class NtPidTable {
    private static final String PID_TABLE_NAME = "Pid";
    private static final String KP_ENTRY_NAME = "Kp";
    private static final String KI_ENTRY_NAME = "Ki";
    private static final String KD_ENTRY_NAME = "Kd";
    private static final String KF_ENTRY_NAME = "Kf";
    private static final String PID_INPUT_ENTRY_NAME = "Input";
    private final NtTable mTable;

    public NtPidTable(double kP, double kI, double kD, double kF, ObservableDoubleValue pidInput) {
        mTable = new NtTable.Builder(PID_TABLE_NAME)
                .addDoubleEntry(KP_ENTRY_NAME, 0)
                .addDoubleEntry(KI_ENTRY_NAME, 0)
                .addDoubleEntry(KD_ENTRY_NAME, 0)
                .addDoubleEntry(KD_ENTRY_NAME, 0)
                .addDoubleEntry(KF_ENTRY_NAME, 0)
                .addDoubleEntry(PID_INPUT_ENTRY_NAME, 0)
                .build();

        setPIDFValues(kP, kI, kD, kF);
        addPidInputListener(pidInput);
    }

    public PidController generateController() {
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

    private void addPidInputListener(ObservableDoubleValue pidInput) {
        pidInput.addChangeListener(event -> updatePidInput(event.getNewValue()));
    }

    private void updatePidInput(double value) {
        mTable.setAsDouble(PID_INPUT_ENTRY_NAME, value);
    }
}
