package com.connect.models;

public interface ConnectorStatusMXbean {
    public String getName();
    public String getState();
    public void setName(String name);
    public void setState(String state);
    public String getWorkerId();
    public void SetWorkerId(String workerId);

}
