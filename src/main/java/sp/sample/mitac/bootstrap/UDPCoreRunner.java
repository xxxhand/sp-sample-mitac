package sp.sample.mitac.bootstrap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import sp.sample.mitac.applications.interfaces.IUDPCoreApplication;
import sp.sample.mitac.shared.UDPCoreConfig;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPCoreRunner {
    private File configFile;

    public UDPCoreRunner(File configFile) {
        this.configFile = configFile;
    }

    public void run() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode appConfig = mapper.readTree(this.configFile);
        UDPCoreConfig.setConfig(appConfig.get("udpModule"));

        DatagramSocket socketServer = new DatagramSocket(UDPCoreConfig.getConfig().get("udpPort").asInt());
        System.out.println("UDP server is running... ");
        byte[] receiveData = new byte[49];

        Injector injector = Guice.createInjector(AppInjector.defaultInjector());
        IUDPCoreApplication coreApplication = injector.getInstance(IUDPCoreApplication.class);

        ExecutorService exec = Executors.newFixedThreadPool(UDPCoreConfig.getConfig().get("maxPoolSize").asInt());
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socketServer.receive(receivePacket);
            CompletableFuture.runAsync(() -> coreApplication.execute(receivePacket.getData()), exec);
        }
    }
}
