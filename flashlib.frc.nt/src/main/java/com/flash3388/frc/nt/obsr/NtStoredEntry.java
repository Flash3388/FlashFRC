package com.flash3388.frc.nt.obsr;

import com.flash3388.flashlib.net.obsr.StoredEntry;
import com.flash3388.flashlib.net.obsr.Value;
import com.flash3388.flashlib.net.obsr.ValueProperty;
import com.flash3388.flashlib.net.obsr.ValueType;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NtStoredEntry implements StoredEntry {

    private final NetworkTableEntry mEntry;

    public NtStoredEntry(NetworkTableEntry entry) {
        mEntry = entry;
    }

    @Override
    public ValueProperty valueProperty() {
        return null;
    }

    @Override
    public Value getValue() {
        return Helper.ntValueToObsr(mEntry.getValue());
    }

    @Override
    public ValueType getType() {
        return Helper.ntValueTypeToObsr(mEntry.getType());
    }

    @Override
    public boolean isEmpty() {
        return !mEntry.exists();
    }

    @Override
    public byte[] getRaw(byte[] defaultValue) {
        return mEntry.getRaw(defaultValue);
    }

    @Override
    public boolean getBoolean(boolean defaultValue) {
        return mEntry.getBoolean(defaultValue);
    }

    @Override
    public int getInt(int defaultValue) {
        return (int) mEntry.getInteger(defaultValue);
    }

    @Override
    public long getLong(long defaultValue) {
        return mEntry.getInteger(defaultValue);
    }

    @Override
    public double getDouble(double defaultValue) {
        return mEntry.getDouble(defaultValue);
    }

    @Override
    public String getString(String defaultValue) {
        return mEntry.getString(defaultValue);
    }

    @Override
    public boolean[] getBooleanArray(boolean[] defaultValue) {
        return mEntry.getBooleanArray(defaultValue);
    }

    @Override
    public int[] getIntArray(int[] defaultValue) {
        return Helper.longArrayToIntArray(mEntry.getIntegerArray(Helper.intArrayToLongArray(defaultValue)));
    }

    @Override
    public double[] getDoubleArray(double[] defaultValue) {
        return mEntry.getDoubleArray(defaultValue);
    }

    @Override
    public String[] getStringArray(String[] defaultValue) {
        return mEntry.getStringArray(defaultValue);
    }

    @Override
    public void clearValue() {
        mEntry.unpublish();
    }

    @Override
    public void delete() {
        mEntry.unpublish();
    }

    @Override
    public void setRaw(byte[] value) {
        mEntry.setRaw(value);
    }

    @Override
    public void setBoolean(boolean value) {
        mEntry.setBoolean(value);
    }

    @Override
    public void setInt(int value) {
        mEntry.setInteger(value);
    }

    @Override
    public void setLong(long value) {
        mEntry.setInteger(value);
    }

    @Override
    public void setDouble(double value) {
        mEntry.setDouble(value);
    }

    @Override
    public void setString(String value) {
        mEntry.setString(value);
    }

    @Override
    public void setBooleanArray(boolean[] value) {
        mEntry.setBooleanArray(value);
    }

    @Override
    public void setIntArray(int[] value) {
        mEntry.setIntegerArray(Helper.intArrayToLongArray(value));
    }

    @Override
    public void setDoubleArray(double[] value) {
        mEntry.setDoubleArray(value);
    }

    @Override
    public void setStringArray(String[] value) {
        mEntry.setStringArray(value);
    }
}
