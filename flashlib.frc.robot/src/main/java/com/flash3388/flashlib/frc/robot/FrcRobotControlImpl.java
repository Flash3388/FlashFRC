package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.frc.robot.hid.FrcHidInterface;
import com.flash3388.flashlib.frc.robot.io.RoboRioIoInterface;
import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.frc.robot.logging.FrcLoggerFactory;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotMode;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotModeSupplier;
import com.flash3388.flashlib.frc.robot.time.FpgaClock;
import com.flash3388.flashlib.hid.HidInterface;
import com.flash3388.flashlib.hid.generic.GenericHidInterface;
import com.flash3388.flashlib.io.IoInterface;
import com.flash3388.flashlib.robot.RobotFactory;
import com.flash3388.flashlib.robot.modes.RobotMode;
import com.flash3388.flashlib.scheduling.Scheduler;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.util.resources.ResourceHolder;
import edu.wpi.first.wpilibj.DriverStation;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.function.Supplier;

public class FrcRobotControlImpl implements FrcRobotControl {

    private final ResourceHolder mResourceHolder;
    private final Scheduler mScheduler;
    private final Clock mClock;
    private final Supplier<FrcRobotMode> mRobotModeSupplier;
    private final IoInterface mIoInterface;
    private final HidInterface mHidInterface;
    private final Logger mLogger;
    private final RobotFileSystem mRobotFileSystem;

    public FrcRobotControlImpl(ResourceHolder resourceHolder, RobotConfiguration robotConfiguration) {
        mResourceHolder = resourceHolder;

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());
        mClock = new FpgaClock();
        mScheduler = RobotFactory.newDefaultScheduler(mClock, mLogger);

        DriverStation driverStation = DriverStation.getInstance();

        mRobotModeSupplier = new FrcRobotModeSupplier(driverStation);
        mIoInterface = new RoboRioIoInterface();
        mHidInterface = new GenericHidInterface(new FrcHidInterface(driverStation));
        mRobotFileSystem = new RobotFileSystem();
    }

    public FrcRobotControlImpl(ResourceHolder resourceHolder, RobotConfiguration robotConfiguration, Scheduler scheduler) {
        mResourceHolder = resourceHolder;

        mLogger = FrcLoggerFactory.createLogger(robotConfiguration.getLogConfiguration());
        mClock = new FpgaClock();
        mScheduler = scheduler;

        DriverStation driverStation = DriverStation.getInstance();

        mRobotModeSupplier = new FrcRobotModeSupplier(driverStation);
        mIoInterface = new RoboRioIoInterface();
        mHidInterface = new GenericHidInterface(new FrcHidInterface(driverStation));
        mRobotFileSystem = new RobotFileSystem();
    }

    public FrcRobotControlImpl(ResourceHolder resourceHolder, Scheduler scheduler) {
        this(resourceHolder, RobotConfiguration.defaultConfiguration(), scheduler);
    }

    public FrcRobotControlImpl(ResourceHolder resourceHolder) {
        this(resourceHolder, RobotConfiguration.defaultConfiguration());
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

    @Override
    public RobotFileSystem getFileSystem() {
        return mRobotFileSystem;
    }

    @Override
    public void registerCloseables(Collection<? extends AutoCloseable> closeables) {
        mResourceHolder.add(closeables);
    }
}
