package com.banking.models;

public class Application {
private int pendingId;
private String userName;

public Application() {
	super();
}


public Application(int pendingId, String userName) {
	this.pendingId = pendingId;
	this.userName = userName;
}


public int getPendingId() {
	return pendingId;
}


public void setPendingId(int pendingId) {
	this.pendingId = pendingId;
}


public String getUserId() {
	return userName;
}


public void setUserId(String userName) {
	this.userName = userName;
}


@Override
public String toString() {
	return "Application [pendingId=" + pendingId + ", userId=" + userName + "]";
}




}
