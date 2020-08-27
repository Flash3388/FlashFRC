package com.flash3388.frc.nt.ntp;

import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NtNtpFactory {

    private static final String REQUEST_ENTRY = "request";
    private static final String REQUEST_RECEIVE_ENTRY = "receive";
    private static final String REQUEST_SEND_ENTRY = "send";

    private NtNtpFactory() {}

    public static NtNtpClient createClient(NtpClock clock, NetworkTable table) {
        return new NtNtpClient(clock,
                table.getEntry(REQUEST_ENTRY),
                table.getEntry(REQUEST_RECEIVE_ENTRY),
                table.getEntry(REQUEST_SEND_ENTRY));
    }

    public static NtNtpClient createClient(NtpClock clock, NetworkTableInstance instance, String tableName) {
        return createClient(clock, instance.getTable(tableName));
    }

    public static NtNtpClient createClient(NtpClock clock, String tableName) {
        return createClient(clock, NetworkTableInstance.getDefault(), tableName);
    }

    public static NtNtpServer createServer(Clock clock, NetworkTable table) {
        return new NtNtpServer(clock,
                table.getEntry(REQUEST_ENTRY),
                table.getEntry(REQUEST_RECEIVE_ENTRY),
                table.getEntry(REQUEST_SEND_ENTRY));
    }

    public static NtNtpServer createServer(Clock clock, NetworkTableInstance instance, String tableName) {
        return createServer(clock, instance.getTable(tableName));
    }

    public static NtNtpServer createServer(Clock clock, String table) {
        return createServer(clock, NetworkTableInstance.getDefault(), table);
    }
}
