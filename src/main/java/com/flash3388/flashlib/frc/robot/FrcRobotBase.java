package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.hid.FrcHidInterface;
import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.frc.robot.logging.FrcLoggerFactory;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotModeSupplier;
import com.flash3388.flashlib.frc.robot.time.FpgaClock;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.RobotFactory;
import com.flash3388.flashlib.robot.RunningRobot;
import com.flash3388.flashlib.robot.hid.HidInterface;
import com.flash3388.flashlib.robot.modes.RobotModeSupplier;
import com.flash3388.flashlib.robot.scheduling.Scheduler;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.util.resources.Resource;
import edu.wpi.first.wpilibj.RobotBase;
import org.slf4j.Logger;

import java.util.Collection;

public abstract class FrcRobotBase extends RobotBase implements Robot {

    private final Scheduler mScheduler;
    private final Clock mClock;
    private final RobotModeSupplier mRobotModeSupplier;
    private final HidInterface mHidInterface;
    private final Logger mLogger;
    private final RobotFileSystem mRobotFileSystem;

    public FrcRobotBase(RobotConfiguration robotConfiguration) {
        RunningRobot.setInstance(this);

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());

        mClock = new FpgaClock();

        mScheduler = RobotFactory.newDefaultScheduler(mClock, mLogger);

        // m_ds -> from super -> protected final DriverStation m_ds
        mRobotModeSupplier = new FrcRobotModeSupplier(m_ds);
        mHidInterface = new FrcHidInterface(m_ds);
        mRobotFileSystem = new RobotFileSystem();
    }

    public FrcRobotBase(RobotConfiguration robotConfiguration, Scheduler scheduler) {
        RunningRobot.setInstance(this);

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());

        mClock = new FpgaClock();

        mScheduler = scheduler;

        // m_ds -> from super -> protected final DriverStation m_ds
        mRobotModeSupplier = new FrcRobotModeSupplier(m_ds);
        mHidInterface = new FrcHidInterface(m_ds);
        mRobotFileSystem = new RobotFileSystem();
    }

    public FrcRobotBase(Scheduler scheduler) {
        this(RobotConfiguration.defaultConfiguration(), scheduler);
    }

    public FrcRobotBase() {
        this(RobotConfiguration.defaultConfiguration());
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

    public RobotFileSystem getFileSystem() {
        return mRobotFileSystem;
    }

    @Override
    public void registerResources(Collection<? extends Resource> resources) {
        throw new UnsupportedOperationException("no need to register resources for termination in FRC robots");
    }
}
