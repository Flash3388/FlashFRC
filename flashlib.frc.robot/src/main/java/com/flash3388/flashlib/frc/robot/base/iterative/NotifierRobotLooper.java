package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.robot.base.iterative.RobotLooper;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.NotifierJNI;

import java.util.concurrent.TimeUnit;

public class NotifierRobotLooper implements RobotLooper {

    private static final Time DEFAULT_LOOP_PERIOD = Time.milliseconds(20);

    private final Time mLoopPeriod;
    private int mNotifierHandle;

    public NotifierRobotLooper(Time loopPeriod) {
        if (!loopPeriod.isValid()) {
            throw new IllegalArgumentException("loopPeriod not valid time");
        }

        mLoopPeriod = loopPeriod;
        mNotifierHandle = NotifierJNI.initializeNotifier();

        NotifierJNI.setNotifierName(mNotifierHandle, "TimedRobot");
        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Timed);
    }

    public NotifierRobotLooper() {
        this(DEFAULT_LOOP_PERIOD);
    }

    @Override
    public Time getLoopRunPeriod() {
        return mLoopPeriod;
    }

    @Override
    public void startLooping(Clock clock, Runnable loopTask) {
        Time expirationTime = clock.currentTime().add(mLoopPeriod);
        updateAlarm(expirationTime);

        while (true) {
            long currentTime = NotifierJNI.waitForNotifierAlarm(mNotifierHandle);
            if (currentTime == 0) {
                break;
            }

            expirationTime = expirationTime.add(mLoopPeriod);
            updateAlarm(expirationTime);

            loopTask.run();
        }
    }

    @Override
    public void stop() {
        NotifierJNI.stopNotifier(mNotifierHandle);
        NotifierJNI.cleanNotifier(mNotifierHandle);
        mNotifierHandle = -1;
    }

    @Override
    protected void finalize() {
        if (mNotifierHandle >= 0) {
            stop();
        }
    }

    private void updateAlarm(Time expirationTime) {
        NotifierJNI.updateNotifierAlarm(mNotifierHandle, expirationTime.toUnit(TimeUnit.MICROSECONDS).value());
    }
}
