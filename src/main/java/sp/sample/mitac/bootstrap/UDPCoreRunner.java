package sp.sample.mitac.bootstrap;

import com.google.inject.Inject;
import com.google.inject.Injector;
import sp.sample.mitac.applications.interfaces.IUDPCoreApplication;
import sp.sample.mitac.shared.UDPCoreConfig;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPCoreRunner {
    private final Injector injector = InjectorFactory.getInjector();

    public UDPCoreRunner() {

    }

    public void run() throws Exception {
        DatagramSocket socketServer = new DatagramSocket(UDPCoreConfig.getConfig().get("udpPort").asInt());
        System.out.println("UDP server is running... ");
        byte[] receiveData = new byte[49];

        IUDPCoreApplication coreApplication = injector.getInstance(IUDPCoreApplication.class);

        ExecutorService exec = Executors.newFixedThreadPool(UDPCoreConfig.getConfig().get("maxPoolSize").asInt());
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socketServer.receive(receivePacket);
            CompletableFuture.runAsync(() -> coreApplication.execute(receivePacket.getData()), exec);
        }
    }
}
