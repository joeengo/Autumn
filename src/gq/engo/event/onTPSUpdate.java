package gq.engo.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class onTPSUpdate extends Event {
    private static final HandlerList handlers = new HandlerList();
    private double old;
    private double ne;

    public onTPSUpdate(double n, double o) {
        old = o;
        ne = n;
    }

    public double getOldTPS() {
        return old;
    }

    public double getNewTPS() {
        return ne;
    }


    public HandlerList getHandlers() {
        return handlers;
    }
}
