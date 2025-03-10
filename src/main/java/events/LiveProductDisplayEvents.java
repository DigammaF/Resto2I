package events;

import views.LiveProductDisplay;

public class LiveProductDisplayEvents {
    public static class LiveProductDisplayEvent { }

    public static class CostChanged extends LiveProductDisplayEvent { }

    public static class Removed extends LiveProductDisplayEvent {
        public final LiveProductDisplay liveProductDisplay;

        public Removed(LiveProductDisplay liveProductDisplay) {
            this.liveProductDisplay = liveProductDisplay;
        }
    }
}
