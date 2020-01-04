package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;

public abstract class IterativeFrcRobot extends IterativeFrcRobotBase {

    private boolean mExit;

    public IterativeFrcRobot(RobotConfiguration configuration, Scheduler scheduler) {
        super(configuration, scheduler, DEFAULT_LOOP_PERIOD);
        initInstance();
    }

    public IterativeFrcRobot(RobotConfiguration configuration) {
        super(configuration, DEFAULT_LOOP_PERIOD);
        initInstance();
    }

    public IterativeFrcRobot() {
        super();
        initInstance();
    }

    @Override
    public void startCompetition() {
        robotInit();

        HAL.observeUserProgramStarting();

        while (!Thread.interrupted()) {
            m_ds.waitForData();
            if (mExit) {
                break;
            }

            loop();
        }
    }

    @Override
    public void endCompetition() {
        mExit = true;
        m_ds.wakeupWaitForData();
    }

    private void initInstance() {
        mExit = false;
        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Iterative);
    }
}
