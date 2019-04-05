package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.robot.RobotFactory;
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

    public TimedFrcRobot(Scheduler scheduler, Time loopPeriod) {
        super(scheduler);

        if (!loopPeriod.isValid()) {
            throw new IllegalArgumentException("loopPeriod not valid time");
        }

        mLoopPeriod = loopPeriod;
        mNotifier = new Notifier(this::loop);
    }

    public TimedFrcRobot(Time loopPeriod) {
        this(RobotFactory.newDefaultScheduler(), loopPeriod);
    }

    public TimedFrcRobot() {
        this(DEFAULT_LOOP_PERIOD);
    }

    @Override
    public void startCompetition() {
        robotInit();

        HAL.observeUserProgramStarting();

        long periodSeconds = mLoopPeriod.getAsUnit(TimeUnit.SECONDS).getValue();
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
