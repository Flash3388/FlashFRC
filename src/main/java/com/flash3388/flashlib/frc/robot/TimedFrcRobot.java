package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.robot.scheduling.Scheduler;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.NotifierJNI;

import java.util.concurrent.TimeUnit;

public abstract class TimedFrcRobot extends IterativeFrcRobotBase {

    private final int mNotifierHandle;
    private final Time mLoopPeriod;

    public TimedFrcRobot(RobotConfiguration configuration, Scheduler scheduler, Time loopPeriod) {
        super(configuration, scheduler, loopPeriod);

        validateCtorParams(loopPeriod);

        mLoopPeriod = loopPeriod;
        mNotifierHandle = NotifierJNI.initializeNotifier();
        initInstance();
    }

    public TimedFrcRobot(RobotConfiguration configuration, Time loopPeriod) {
        super(configuration);

        validateCtorParams(loopPeriod);

        mLoopPeriod = loopPeriod;
        mNotifierHandle = NotifierJNI.initializeNotifier();
        initInstance();
    }

    public TimedFrcRobot(RobotConfiguration configuration) {
        this(configuration, DEFAULT_LOOP_PERIOD);
    }

    public TimedFrcRobot() {
        this(RobotConfiguration.defaultConfiguration(), DEFAULT_LOOP_PERIOD);
    }

    @Override
    public void startCompetition() {
        robotInit();

        HAL.observeUserProgramStarting();

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
    public void endCompetition() {
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
