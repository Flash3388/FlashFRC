package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.robot.base.iterative.RobotLooper;
import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.DriverStation;

public class DsDataLooper implements RobotLooper {

    private boolean mExit;

    public DsDataLooper() {
        mExit = false;
        HAL.report(FRCNetComm.tResourceType.kResourceType_Framework, FRCNetComm.tInstances.kFramework_Iterative);
    }

    @Override
    public void startLooping(Clock clock, Runnable loopTask) {
        while (!Thread.interrupted()) {
            DriverStation.waitForData();
            if (mExit) {
                break;
            }

            loopTask.run();
        }
    }

    @Override
    public void stop() {
        mExit = true;
        DriverStation.wakeupWaitForData();
    }
}
