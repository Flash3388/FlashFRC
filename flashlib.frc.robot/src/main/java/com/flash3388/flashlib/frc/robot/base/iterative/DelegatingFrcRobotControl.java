package com.flash3388.flashlib.frc.robot.base.iterative;

import com.flash3388.flashlib.frc.robot.FrcRobotControl;
import com.flash3388.flashlib.frc.robot.RuntimeType;
import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.robot.base.DelegatingRobotControl;

public class DelegatingFrcRobotControl extends DelegatingRobotControl implements FrcRobotControl {

    private final FrcRobotControl mControl;

    protected DelegatingFrcRobotControl(FrcRobotControl robotControl) {
        super(robotControl);
        mControl = robotControl;
    }

    @Override
    public RobotFileSystem getFileSystem() {
        return mControl.getFileSystem();
    }

    @Override
    public RuntimeType getRuntimeType() {
        return mControl.getRuntimeType();
    }
}
