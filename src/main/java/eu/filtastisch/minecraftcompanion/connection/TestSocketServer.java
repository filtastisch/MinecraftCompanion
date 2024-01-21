package eu.filtastisch.minecraftcompanion.connection;

import eu.filtastisch.minecraftcompanion.MinecraftCompanion;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.logging.Level;

public class TestSocketServer extends WebSocketServer {
    public TestSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Bukkit.getLogger().log (Level.FINE ,"Neue Verbindung: " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        JSONObject data = new JSONObject();
        data.put("server-name", "TestServer");
        data.put("connected", true);

        JSONArray buttons = new JSONArray();
        buttons.put(new JSONObject().put("button-name", "testButton-1").put("state", true));
        buttons.put(new JSONObject().put("button-name", "testButton-2").put("state", false));

        data.put("buttons", buttons);
        conn.send(data.toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Verbindung geschlossen");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message.equals("ch-block")){
            MinecraftCompanion.getInstance().executeSync(() -> {
                if (MinecraftCompanion.getInstance().getButtonLoc().getBlock().getType() == Material.RED_WOOL)
                    MinecraftCompanion.getInstance().getButtonLoc().getBlock().setType(Material.REDSTONE_BLOCK);
                else
                    MinecraftCompanion.getInstance().getButtonLoc().getBlock().setType(Material.RED_WOOL);
            });
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server gestartet auf port: " + this.getPort());
    }
}
