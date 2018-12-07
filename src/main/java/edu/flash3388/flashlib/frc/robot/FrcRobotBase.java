package edu.flash3388.flashlib.frc.robot;

import edu.flash3388.flashlib.frc.robot.hid.FrcHidInterface;
import edu.flash3388.flashlib.frc.robot.modes.FrcRobotModeSupplier;
import edu.flash3388.flashlib.robot.RobotInterface;
import edu.flash3388.flashlib.robot.hid.HidInterface;
import edu.flash3388.flashlib.robot.modes.RobotModeSupplier;
import edu.wpi.first.wpilibj.RobotBase;

public abstract class FrcRobotBase extends RobotBase implements RobotInterface {

    private final RobotModeSupplier mRobotModeSupplier;
    private final HidInterface mHidInterface;

    public FrcRobotBase() {
        // m_ds -> from super -> protected final DriverStation m_ds
        mRobotModeSupplier = new FrcRobotModeSupplier(m_ds);
        mHidInterface = new FrcHidInterface(m_ds);
    }

    @Override
    public RobotModeSupplier getModeSupplier() {
        return mRobotModeSupplier;
    }

    @Override
    public HidInterface getHidInterface() {
        return mHidInterface;
    }
}
