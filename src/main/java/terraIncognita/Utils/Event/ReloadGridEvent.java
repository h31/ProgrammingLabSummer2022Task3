package terraIncognita.Utils.Event;

import javafx.event.Event;
import javafx.event.EventType;

public class ReloadGridEvent extends Event {
    public static final EventType<ReloadGridEvent> RELOAD_GRID_EVENT_TYPE = new EventType(ANY);

    public ReloadGridEvent() {
        super(RELOAD_GRID_EVENT_TYPE);
    }

    public void invokeHandler(ReloadEventHandler handler) {
        handler.onFire();
    }

}
