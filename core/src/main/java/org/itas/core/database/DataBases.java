package org.itas.core.database;

import org.itas.core.database.DBPoolImpl.DBPoolBuilder;
import org.itas.core.database.DBSyncImpl.DBSyncBuilder;
import org.itas.core.database.DBSyncService.DBSyncThreadBuilder;

public final class DataBases {

  public static DBPoolBuilder makdDBPoolBuilder() {
	return new DBPoolBuilder();
  }
  
  public static DBSyncBuilder makeDBSyncBuilder() {
	return new DBSyncImpl.DBSyncBuilder();
  }
	
  public static DBSyncThreadBuilder makdDBSyncThreadBuilder() {
	return new DBSyncThreadBuilder();
  }
  
  private DataBases() {
  }
	
}
