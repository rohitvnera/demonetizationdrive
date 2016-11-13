package com.ampdev.platform.framework.dataaccess;


public interface DatabaseSession {
	
	public void startTransaction();
	
	public void create();
	
	public void update();
	
	public void delete();
	
	//get needs to be added here 

}
