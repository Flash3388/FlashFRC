package com.flash3388.flashlib.frc.robot.base;

import com.flash3388.flashlib.frc.robot.RobotConfiguration;
import com.flash3388.flashlib.frc.robot.hid.FrcHidInterface;
import com.flash3388.flashlib.frc.robot.io.RoboRioIoInterface;
import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.frc.robot.logging.FrcLoggerFactory;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotModeSupplier;
import com.flash3388.flashlib.frc.robot.time.FpgaClock;
import com.flash3388.flashlib.robot.RobotControl;
import com.flash3388.flashlib.robot.RunningRobot;
import com.flash3388.flashlib.hid.HidInterface;
import com.flash3388.flashlib.hid.generic.GenericHidInterface;
import com.flash3388.flashlib.io.IoInterface;
import com.flash3388.flashlib.robot.base.RobotFactory;
import com.flash3388.flashlib.robot.modes.RobotMode;
import com.flash3388.flashlib.scheduling.Scheduler;
import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.wpilibj.RobotBase;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class FrcRobotControlBase extends RobotBase implements RobotControl {

    private final Scheduler mScheduler;
    private final Clock mClock;
    private final Supplier<FrcRobotMode> mRobotModeSupplier;
    private final IoInterface mIoInterface;
    private final HidInterface mHidInterface;
    private final Logger mLogger;
    private final RobotFileSystem mRobotFileSystem;

    public FrcRobotControlBase(RobotConfiguration robotConfiguration) {
        RunningRobot.setInstance(this);

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());

        mClock = new FpgaClock();

        mScheduler = RobotFactory.newDefaultScheduler(mClock, mLogger);

        // m_ds -> from super -> protected final DriverStation m_ds
        mRobotModeSupplier = new FrcRobotModeSupplier(m_ds);
        mIoInterface = new RoboRioIoInterface();
        mHidInterface = new GenericHidInterface(new FrcHidInterface(m_ds));
        mRobotFileSystem = new RobotFileSystem();
    }

    public FrcRobotControlBase(RobotConfiguration robotConfiguration, Scheduler scheduler) {
        RunningRobot.setInstance(this);

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());

        mClock = new FpgaClock();

        mScheduler = scheduler;

        // m_ds -> from super -> protected final DriverStation m_ds
        mRobotModeSupplier = new FrcRobotModeSupplier(m_ds);
        mIoInterface = new RoboRioIoInterface();
        mHidInterface = new GenericHidInterface(new FrcHidInterface(m_ds));
        mRobotFileSystem = new RobotFileSystem();
    }

    public FrcRobotControlBase(Scheduler scheduler) {
        this(RobotConfiguration.defaultConfiguration(), scheduler);
    }

    public FrcRobotControlBase() {
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
    public Supplier<? extends RobotMode> getModeSupplier() {
        return mRobotModeSupplier;
    }

    @Override
    public IoInterface getIoInterface() {
        return mIoInterface;
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
    public void registerCloseables(Collection<? extends AutoCloseable> closeables) {
        throw new UnsupportedOperationException("no need to register resources for termination in FRC robots");
    }
}
