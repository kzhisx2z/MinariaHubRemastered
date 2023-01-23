package fr.kx2zh.minaria.hub.common.managers;

import fr.kx2zh.minaria.hub.Hub;

import java.util.logging.Level;

public abstract class AbstractManager implements EntryPoints {

    protected final Hub hub;

    public AbstractManager(Hub hub) {
        this.hub = hub;
        hub.getEventBus().registerManager(this);
    }

    public void log(Level level, String message) {
        hub.getLogger().log(level, "[" + getClass().getSimpleName() + "] " + message);
    }

}
