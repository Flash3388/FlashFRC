package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.robot.scheduling.Scheduler;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.util.concurrent.Interrupts;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Notifier;

import java.util.concurrent.TimeUnit;

public abstract class TimedFrcRobot extends IterativeFrcRobotBase {

    protected static final Time DEFAULT_LOOP_PERIOD = Time.milliseconds(20);
    private static final long MAIN_THREAD_SLEEP_MS = 1000 * 60 * 60 * 24;

    private final Notifier mNotifier;
    private final Time mLoopPeriod;

    public TimedFrcRobot(RobotConfiguration configuration, Scheduler scheduler, Time loopPeriod) {
        super(configuration, scheduler);

        if (!loopPeriod.isValid()) {
            throw new IllegalArgumentException("loopPeriod not valid time");
        }

        mLoopPeriod = loopPeriod;
        mNotifier = new Notifier(this::loop);
    }

    public TimedFrcRobot(RobotConfiguration configuration, Time loopPeriod) {
        super(configuration);

        mLoopPeriod = loopPeriod;
        mNotifier = new Notifier(this::loop);
    }

    public TimedFrcRobot(RobotConfiguration configuration) {
        super(configuration);

        mLoopPeriod = DEFAULT_LOOP_PERIOD;
        mNotifier = new Notifier(this::loop);
    }

    public TimedFrcRobot(Time loopPeriod) {
        super();

        mLoopPeriod = loopPeriod;
        mNotifier = new Notifier(this::loop);

    }

    public TimedFrcRobot() {
        this(DEFAULT_LOOP_PERIOD);
    }

    @Override
    public void startCompetition() {
        robotInit();

        HAL.observeUserProgramStarting();

        long periodSeconds = mLoopPeriod.toUnit(TimeUnit.SECONDS).value();
        mNotifier.startPeriodic(periodSeconds);

        while (true) {
            try {
                Thread.sleep(MAIN_THREAD_SLEEP_MS);
            } catch (InterruptedException e) {
                Interrupts.preserveInterruptState();
            }
        }
    }
}
