package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.hid.FrcHidInterface;
import com.flash3388.flashlib.frc.robot.logging.FrcLoggerFactory;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotModeSupplier;
import com.flash3388.flashlib.frc.robot.time.FpgaClock;
import edu.flash3388.flashlib.robot.Robot;
import edu.flash3388.flashlib.robot.hid.HidInterface;
import edu.flash3388.flashlib.robot.modes.RobotModeSupplier;
import edu.flash3388.flashlib.robot.scheduling.Scheduler;
import edu.flash3388.flashlib.time.Clock;
import edu.flash3388.flashlib.util.resources.Resource;
import edu.wpi.first.wpilibj.RobotBase;
import org.slf4j.Logger;

public abstract class FrcRobotBase extends RobotBase implements Robot {

    private final Scheduler mScheduler;
    private final Clock mClock;
    private final RobotModeSupplier mRobotModeSupplier;
    private final HidInterface mHidInterface;
    private final Logger mLogger;

    public FrcRobotBase(RobotConfiguration robotConfiguration, Scheduler scheduler) {
        mScheduler = scheduler;

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());

        mClock = new FpgaClock();

        // m_ds -> from super -> protected final DriverStation m_ds
        mRobotModeSupplier = new FrcRobotModeSupplier(m_ds);
        mHidInterface = new FrcHidInterface(m_ds);
    }

    public FrcRobotBase(Scheduler scheduler) {
        this(RobotConfiguration.defaultConfiguration(), scheduler);
    }

    @Override
    public Scheduler getScheduler() {
        return mScheduler;
    }

    @Override
    public Clock getClock() {
        return mClock;
    }

    @Override
    public RobotModeSupplier getModeSupplier() {
        return mRobotModeSupplier;
    }

    @Override
    public HidInterface getHidInterface() {
        return mHidInterface;
    }

    @Override
    public Logger getLogger() {
        return mLogger;
    }

    @Override
    public void registerResources(Resource... resources) {
        throw new UnsupportedOperationException("no need to register resources for termination in FRC robots");
    }
}
