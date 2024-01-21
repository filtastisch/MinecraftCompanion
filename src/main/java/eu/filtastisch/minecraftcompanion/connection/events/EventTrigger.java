package eu.filtastisch.minecraftcompanion.connection.events;

import eu.filtastisch.minecraftcompanion.connection.listener.AppInputEvent;

public class EventTrigger {
    private AppInputEventListener inputEventListener;

    public void performEvent(String msg){
        AppInputEvent event = new AppInputEvent(msg);
        inputEventListener.onAppInputEventReceived(event);
    }

    public void setInputEventListener(AppInputEventListener inputEventListener) {
        this.inputEventListener = inputEventListener;
    }
}
