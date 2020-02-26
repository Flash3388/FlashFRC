package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.base.IterativeFrcRobot;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;

public abstract class DsLoopingFrcRobotControl extends LoopingRobotControl {

    private boolean mExit;

    public DsLoopingFrcRobotControl(RobotConfiguration configuration, Scheduler scheduler, IterativeFrcRobot.Initializer robotInitializer) {
        super(configuration, scheduler, DEFAULT_LOOP_PERIOD, robotInitializer);
        initInstance();
    }

    public DsLoopingFrcRobotControl(RobotConfiguration configuration, IterativeFrcRobot.Initializer robotInitializer) {
        super(configuration, robotInitializer);
        initInstance();
    }

    public DsLoopingFrcRobotControl(IterativeFrcRobot.Initializer robotInitializer) {
        super(robotInitializer);
        initInstance();
    }

    @Override
    protected void robotLoop() {
        while (!Thread.interrupted()) {
            m_ds.waitForData();
            if (mExit) {
                break;
            }

            loop();
        }
    }

    @Override
    protected void robotStop() {
        mExit = true;
        m_ds.wakeupWaitForData();
    }

    private void initInstance() {
        mExit = false;
        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Iterative);
    }
}
