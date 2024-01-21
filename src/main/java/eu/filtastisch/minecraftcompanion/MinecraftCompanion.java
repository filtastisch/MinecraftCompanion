package eu.filtastisch.minecraftcompanion;

import eu.filtastisch.minecraftcompanion.commands.SetupCommand;
import eu.filtastisch.minecraftcompanion.connection.TestSocketServer;
import eu.filtastisch.minecraftcompanion.connection.events.EventTrigger;
import eu.filtastisch.minecraftcompanion.connection.listener.AppInputEvent;
import eu.filtastisch.minecraftcompanion.connection.listener.AppInputListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public final class MinecraftCompanion extends JavaPlugin {

    private String currentMidi;

    private boolean changing;

    private static MinecraftCompanion instance;

    private ServerSocket serverSocket;
    TestSocketServer server;
    public Location buttonLoc;

    @Override
    public void onEnable() {
        instance = this;
        this.server = new TestSocketServer(25578);
        server.start();
        Objects.requireNonNull(this.getCommand("setup")).setExecutor(new SetupCommand());
    }

    @Override
    public void onDisable() {
        try {
            server.stop();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startAppListen() {
        try {
            serverSocket = new ServerSocket(25578);
            Thread listener = new Thread(() -> {
                System.out.println("Socket listening on: " + serverSocket.getLocalSocketAddress() + ":" + serverSocket.getLocalPort());
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();

                        InputStream in = clientSocket.getInputStream();
                        DataInputStream dataInputStream = new DataInputStream(in);

                        this.currentMidi = dataInputStream.readUTF();
                        this.changing = true;

                        clientSocket.close();
                    } catch (Exception ignored) {
                    }
                }
            });
            listener.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void runExecutor(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (changing)
                    executeEvent(currentMidi);

                changing = false;
            }
        }.runTaskTimer(this, 1, 1);
    }

    public synchronized void executeEvent(String msg){
        EventTrigger eventTrigger = new EventTrigger();
        eventTrigger.setInputEventListener(new AppInputListener());

        eventTrigger.performEvent(msg);
    }

    public static MinecraftCompanion getInstance() {
        return instance;
    }

    public void setButtonLoc(Location buttonLoc) {
        this.buttonLoc = buttonLoc;
    }

    public Location getButtonLoc() {
        return buttonLoc;
    }

    public void executeSync(Runnable task){
        Bukkit.getScheduler().runTask(this, task);
    }
}
