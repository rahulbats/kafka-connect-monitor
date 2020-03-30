package com.connect.models;

public interface ConnectorTaskStatusMXBean {
    public int getId();
    public void setId(int id);
    public String getState();
    public void setState(String state);
    public String getWorkerId();
    public void setWorkerId(String workerId);
    public String getTrace();
    public void setTrace(String trace);
}
