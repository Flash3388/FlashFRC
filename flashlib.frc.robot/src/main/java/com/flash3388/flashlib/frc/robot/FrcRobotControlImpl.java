package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.app.BasicServiceRegistry;
import com.flash3388.flashlib.app.ServiceRegistry;
import com.flash3388.flashlib.app.net.NetworkInterface;
import com.flash3388.flashlib.frc.io.RoboRioIoInterface;
import com.flash3388.flashlib.frc.robot.hid.FrcHidInterface;
import com.flash3388.flashlib.frc.robot.io.files.RobotFileSystem;
import com.flash3388.flashlib.frc.robot.modes.FrcRobotModeSupplier;
import com.flash3388.flashlib.frc.robot.time.FpgaClock;
import com.flash3388.flashlib.hid.HidInterface;
import com.flash3388.flashlib.hid.generic.weak.WeakHidInterface;
import com.flash3388.flashlib.io.IoInterface;
import com.flash3388.flashlib.io.devices.DeviceInterface;
import com.flash3388.flashlib.io.devices.DeviceInterfaceImpl;
import com.flash3388.flashlib.net.obsr.ObjectStorage;
import com.flash3388.flashlib.robot.RobotFactory;
import com.flash3388.flashlib.robot.modes.RobotModeSupplier;
import com.flash3388.flashlib.scheduling.Scheduler;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.util.FlashLibMainThread;
import com.flash3388.flashlib.util.FlashLibMainThreadImpl;
import com.flash3388.flashlib.util.logging.Logging;
import com.flash3388.flashlib.util.resources.ResourceHolder;
import com.flash3388.flashlib.util.unique.InstanceId;
import com.flash3388.flashlib.util.unique.InstanceIdGenerator;
import edu.wpi.first.wpilibj.RobotBase;
import org.slf4j.Logger;

import java.util.Collection;

public class FrcRobotControlImpl implements FrcRobotControl {

    private static final Logger LOGGER = Logging.getLogger("FRC");

    private final ResourceHolder mResourceHolder;
    private final InstanceId mInstanceId;
    private final FlashLibMainThread mMainThread;
    private final ServiceRegistry mServiceRegistry;
    private final NetworkInterface mNetworkInterface;
    private final Scheduler mScheduler;
    private final Clock mClock;
    private final RobotModeSupplier mRobotModeSupplier;
    private final IoInterface mIoInterface;
    private final HidInterface mHidInterface;
    private final RobotFileSystem mRobotFileSystem;
    private final DeviceInterface mDeviceInterface;

    public FrcRobotControlImpl(ResourceHolder resourceHolder, RobotConfiguration configuration) {
        mResourceHolder = resourceHolder;

        // verifies we can identify the runtime type
        getRuntimeType();

        mInstanceId = InstanceIdGenerator.generate(0);
        mMainThread = new FlashLibMainThreadImpl();
        mClock = new FpgaClock();
        mServiceRegistry = new BasicServiceRegistry(mMainThread);
        mNetworkInterface = new NetworkInterfaceImpl(
                mInstanceId,
                mServiceRegistry,
                mClock,
                mMainThread
        );

        ObjectStorage objectStorage = mNetworkInterface.getMode().isObjectStorageEnabled() ?
                mNetworkInterface.getObjectStorage() :
                new ObjectStorage.Stub();
        mScheduler = RobotFactory.newDefaultScheduler(mClock, objectStorage, mMainThread);

        mRobotModeSupplier = new FrcRobotModeSupplier();
        mIoInterface = new RoboRioIoInterface(mMainThread);
        mHidInterface = new WeakHidInterface(new FrcHidInterface(), mMainThread);
        mRobotFileSystem = new RobotFileSystem();
        mDeviceInterface = new DeviceInterfaceImpl(mMainThread);
    }

    @Override
    public Scheduler getScheduler() {
        return mScheduler;
    }

    @Override
    public InstanceId getInstanceId() {
        return mInstanceId;
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
    public IoInterface getIoInterface() {
        return mIoInterface;
    }

    @Override
    public HidInterface getHidInterface() {
        return mHidInterface;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public RobotFileSystem getFileSystem() {
        return mRobotFileSystem;
    }

    @Override
    public RuntimeType getRuntimeType() {
        if (RobotBase.isReal()) {
            return RuntimeType.REAL;
        }
        if (RobotBase.isSimulation()) {
            return RuntimeType.SIMULATION;
        }

        throw new AssertionError("unknown RuntimeType");
    }

    @Override
    public void registerCloseables(Collection<? extends AutoCloseable> closeables) {
        getMainThread().verifyCurrentThread();
        mResourceHolder.add(closeables);
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return mServiceRegistry;
    }

    @Override
    public NetworkInterface getNetworkInterface() {
        return mNetworkInterface;
    }

    @Override
    public DeviceInterface getDeviceInterface() {
        return mDeviceInterface;
    }

    @Override
    public FlashLibMainThread getMainThread() {
        return mMainThread;
    }
}
