package edu.flash3388.flashlib.frc.robot;

import edu.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.wpi.first.wpilibj.hal.HAL;

public abstract class IterativeFrcRobot extends IterativeFrcRobotBase {

    public IterativeFrcRobot(Scheduler scheduler) {
        super(scheduler);
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
