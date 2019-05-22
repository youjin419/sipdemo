package com.signal.demo.service;

import com.signal.demo.common.instance.SignalInstance;
import com.signal.demo.common.log.SipLogger;
import com.signal.demo.sip.SipSignal;

import java.io.Serializable;

public class ServiceManager extends SipLogger implements Serializable {
    private static ServiceManager serviceManager;
    private static SipSignal sipSignal;
    private boolean isQuit = false;

    public static ServiceManager getInstance() {
        if (serviceManager == null) {
            serviceManager = new ServiceManager();
        }
        return serviceManager;
    }

    public void loop() {
        logger.debug("Service Start");
        this.startService();

        Runtime.getRuntime().addShutdownHook( new Thread( () -> {
            this.isQuit = true;
            this.stopService();
        }));

        while (!isQuit) {
            try {
                Thread.sleep( 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void startService() {
        SignalInstance signalInstance = SignalInstance.getSignalInstance();
        sipSignal = SipSignal.getInstance();
        signalInstance.setSignal(sipSignal);
        sipSignal.init();

        logger.debug("SipSignal {} // Class {}" , sipSignal, this.getClass());
    }

    private void stopService() {
        logger.debug("SipSignal Stop");
        sipSignal.processStop();
    }

}
