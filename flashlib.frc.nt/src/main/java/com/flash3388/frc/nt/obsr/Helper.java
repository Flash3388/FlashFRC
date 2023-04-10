package com.flash3388.frc.nt.obsr;

import com.flash3388.flashlib.net.obsr.ModificationType;
import com.flash3388.flashlib.net.obsr.Value;
import com.flash3388.flashlib.net.obsr.ValueType;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;

public class Helper {

    private Helper() {}

    public static Value ntValueToObsr(NetworkTableValue value) {
        ValueType type = ntValueTypeToObsr(value.getType());
        return new Value(type, value.getValue());
    }

    public static NetworkTableValue obsrValueToNt(Value value) {
        switch (value.getType()) {
            case EMPTY:
                throw new UnsupportedOperationException();
            case RAW:
                return NetworkTableValue.makeRaw(value.getRaw(null));
            case BOOLEAN:
                return NetworkTableValue.makeBoolean(value.getBoolean(false));
            case INT:
                return NetworkTableValue.makeInteger(value.getInt(0));
            case LONG:
                return NetworkTableValue.makeInteger(value.getLong(0));
            case DOUBLE:
                return NetworkTableValue.makeDouble(value.getDouble(0));
            case STRING:
                return NetworkTableValue.makeString(value.getString(null));
            case BOOLEAN_ARRAY:
                return NetworkTableValue.makeBooleanArray(value.getBooleanArray(null));
            case INT_ARRAY:
                return NetworkTableValue.makeIntegerArray(Helper.intArrayToLongArray(value.getIntArray(null)));
            case DOUBLE_ARRAY:
                return NetworkTableValue.makeDoubleArray(value.getDoubleArray(null));
            case STRING_ARRAY:
                return NetworkTableValue.makeStringArray(value.getStringArray(null));
            default:
                throw new AssertionError("unknown type");
        }
    }

    public static ModificationType modificationTypeFromEventKind(NetworkTableEvent event) {
        return ModificationType.UPDATE;
    }

    public static ValueType ntValueTypeToObsr(NetworkTableType type) {
        switch (type) {
            case kUnassigned:
                return ValueType.EMPTY;
            case kBoolean:
                return ValueType.BOOLEAN;
            case kDouble:
                return ValueType.DOUBLE;
            case kString:
                return ValueType.STRING;
            case kRaw:
                return ValueType.RAW;
            case kBooleanArray:
                return ValueType.BOOLEAN_ARRAY;
            case kDoubleArray:
                return ValueType.DOUBLE_ARRAY;
            case kStringArray:
                return ValueType.STRING_ARRAY;
            case kInteger:
                return ValueType.INT;
            case kIntegerArray:
                return ValueType.INT_ARRAY;
            case kFloat:
            case kFloatArray:
                throw new UnsupportedOperationException();
            default:
                throw new AssertionError("unknown type");
        }
    }

    public static int[] longArrayToIntArray(long[] arr) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = (int) arr[i];
        }

        return newArr;
    }

    public static long[] intArrayToLongArray(int[] arr) {
        long[] newArr = new long[arr.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[i];
        }

        return newArr;
    }
}
