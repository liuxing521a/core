package org.itas.core;

public interface DBSyncService extends Ioc {

    public void onInitialized();

    public void onDestroyed();
    
}
