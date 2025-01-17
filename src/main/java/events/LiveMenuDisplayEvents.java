package events;

import views.LiveMenuDisplay;

public class LiveMenuDisplayEvents {
    public static class LiveMenuDisplayEvent { }

    public static class CostChanged extends LiveMenuDisplayEvent { }

    public static class Removed extends LiveMenuDisplayEvent {
        public final LiveMenuDisplay liveMenuDisplay;

        public Removed(LiveMenuDisplay liveMenuDisplay) {
            this.liveMenuDisplay = liveMenuDisplay;
        }
    }
}
