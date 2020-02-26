package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.IterativeFrcRobot;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.NotifierJNI;

import java.util.concurrent.TimeUnit;

public class IntervalLoopingFrcRobotControl extends LoopingRobotControl {

    private final int mNotifierHandle;
    private final Time mLoopPeriod;

    public IntervalLoopingFrcRobotControl(RobotConfiguration configuration, Scheduler scheduler, Time loopPeriod, IterativeFrcRobot.Initializer robotInitializer) {
        super(configuration, scheduler, loopPeriod, robotInitializer);

        validateCtorParams(loopPeriod);

        mLoopPeriod = loopPeriod;
        mNotifierHandle = NotifierJNI.initializeNotifier();
        initInstance();
    }

    public IntervalLoopingFrcRobotControl(RobotConfiguration configuration, Time loopPeriod, IterativeFrcRobot.Initializer robotInitializer) {
        super(configuration, robotInitializer);

        validateCtorParams(loopPeriod);

        mLoopPeriod = loopPeriod;
        mNotifierHandle = NotifierJNI.initializeNotifier();
        initInstance();
    }

    public IntervalLoopingFrcRobotControl(RobotConfiguration configuration, IterativeFrcRobot.Initializer robotInitializer) {
        this(configuration, DEFAULT_LOOP_PERIOD, robotInitializer);
    }

    public IntervalLoopingFrcRobotControl(IterativeFrcRobot.Initializer robotInitializer) {
        this(RobotConfiguration.defaultConfiguration(), DEFAULT_LOOP_PERIOD, robotInitializer);
    }

    @Override
    protected void robotLoop() {
        Time expirationTime = getClock().currentTime().add(mLoopPeriod);
        updateAlarm(expirationTime);

        while (true) {
            long currentTime = NotifierJNI.waitForNotifierAlarm(mNotifierHandle);
            if (currentTime == 0) {
                break;
            }

            expirationTime = expirationTime.add(mLoopPeriod);
            updateAlarm(expirationTime);

            loop();
        }
    }

    @Override
    protected void robotStop() {
        NotifierJNI.stopNotifier(mNotifierHandle);
    }

    @Override
    protected void finalize() {
        NotifierJNI.stopNotifier(mNotifierHandle);
        NotifierJNI.cleanNotifier(mNotifierHandle);
    }

    private void updateAlarm(Time expirationTime) {
        NotifierJNI.updateNotifierAlarm(mNotifierHandle, expirationTime.toUnit(TimeUnit.MICROSECONDS).value());
    }

    private void validateCtorParams(Time loopPeriod) {
        if (!loopPeriod.isValid()) {
            throw new IllegalArgumentException("loopPeriod not valid time");
        }
    }

    private void initInstance() {
        NotifierJNI.setNotifierName(mNotifierHandle, "TimedRobot");
        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Timed);
    }
}
