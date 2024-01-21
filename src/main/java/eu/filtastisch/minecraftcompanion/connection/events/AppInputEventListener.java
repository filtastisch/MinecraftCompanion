package eu.filtastisch.minecraftcompanion.connection.events;

import eu.filtastisch.minecraftcompanion.connection.listener.AppInputEvent;

public interface AppInputEventListener {
    void onAppInputEventReceived(AppInputEvent event);
}
