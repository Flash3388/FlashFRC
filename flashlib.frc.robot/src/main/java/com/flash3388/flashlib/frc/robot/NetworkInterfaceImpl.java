package com.flash3388.flashlib.frc.robot;

import com.flash3388.flashlib.app.ServiceRegistry;
import com.flash3388.flashlib.app.net.NetworkInterface;
import com.flash3388.flashlib.app.net.NetworkingMode;
import com.flash3388.flashlib.net.channels.tcp.TcpRoutingService;
import com.flash3388.flashlib.net.hfcs.HfcsRegistry;
import com.flash3388.flashlib.net.messaging.KnownMessageTypes;
import com.flash3388.flashlib.net.messaging.Messenger;
import com.flash3388.flashlib.net.messaging.MessengerService;
import com.flash3388.flashlib.net.obsr.ObjectStorage;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.util.FlashLibMainThread;
import com.flash3388.flashlib.util.unique.InstanceId;
import com.flash3388.frc.nt.obsr.NtObjectStorage;

public class NetworkInterfaceImpl implements NetworkInterface {

    private final InstanceId mInstanceId;
    private final ServiceRegistry mServiceRegistry;
    private final Clock mClock;
    private final FlashLibMainThread mMainThread;

    private final NetworkingMode mNetworkingMode;
    private final ObjectStorage mObjectStorage;

    public NetworkInterfaceImpl(InstanceId instanceId,
                                ServiceRegistry serviceRegistry,
                                Clock clock,
                                FlashLibMainThread mainThread) {
        mInstanceId = instanceId;
        mServiceRegistry = serviceRegistry;
        mClock = clock;
        mMainThread = mainThread;

        mNetworkingMode = new NetMode();
        mObjectStorage = new NtObjectStorage(
                edu.wpi.first.networktables.NetworkTableInstance.getDefault(),
                instanceId);
    }


    @Override
    public NetworkingMode getMode() {
        return mNetworkingMode;
    }

    @Override
    public ObjectStorage getObjectStorage() {
        return mObjectStorage;
    }

    @Override
    public HfcsRegistry getHfcsRegistry() {
        throw new IllegalStateException("HFCS disabled");
    }

    @Override
    public Messenger newMessenger(KnownMessageTypes messageTypes, MessengerConfiguration configuration) {
        mMainThread.verifyCurrentThread();

        if (configuration.serverMode) {
            TcpRoutingService routingService = new TcpRoutingService(configuration.serverAddress);
            mServiceRegistry.register(routingService);
        }

        MessengerService service = new MessengerService(mInstanceId, mClock, messageTypes, configuration.serverAddress);
        mServiceRegistry.register(service);
        return service;
    }

    private static class NetMode implements NetworkingMode {

        @Override
        public boolean isNetworkingEnabled() {
            return true;
        }

        @Override
        public boolean isObjectStorageEnabled() {
            return true;
        }

        @Override
        public boolean isHfcsEnabled() {
            return false;
        }
    }
}
