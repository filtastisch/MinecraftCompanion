package eu.filtastisch.minecraftcompanion.connection.listener;

import eu.filtastisch.minecraftcompanion.connection.events.AppInputEventListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class AppInputListener implements AppInputEventListener {
    @Override
    public void onAppInputEventReceived(AppInputEvent event) {
        Bukkit.broadcast(Component.text(event.message()));
    }
}
