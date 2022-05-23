package terraIncognita.Utils.Event;

import javafx.event.Event;
import javafx.event.EventHandler;

public abstract class ReloadEventHandler implements EventHandler<ReloadGridEvent> {
    public abstract void onFire();

    @Override
    public void handle(ReloadGridEvent event) {
        event.invokeHandler(this);
    }
}
