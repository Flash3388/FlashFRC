package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.wpi.first.hal.HAL;

public abstract class IterativeFrcRobot extends IterativeFrcRobotBase {

    public IterativeFrcRobot(RobotConfiguration configuration, Scheduler scheduler) {
        super(configuration, scheduler);
    }

    public IterativeFrcRobot(Scheduler scheduler) {
        super(scheduler);
    }

    public IterativeFrcRobot(RobotConfiguration configuration) {
        super(configuration);
    }

    public IterativeFrcRobot() {
        super();
    }

    @Override
    public void startCompetition() {
        robotInit();

        HAL.observeUserProgramStarting();

        while (true) {
            m_ds.waitForData();
            loop();
        }
    }
}
