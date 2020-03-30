package com.connect.models;

public class ConnectorTaskStatus implements ConnectorTaskStatusMXBean {
    private int id;
    private String state;
    private String workerId;
    private String trace;

    public ConnectorTaskStatus(int id, String state, String workerId, String trace){
        this.id=id;
        this.state=state;
        this.workerId = workerId;
        this.trace = trace;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getWorkerId() {
        return workerId;
    }

    @Override
    public void setWorkerId(String workerId) {
       this.workerId = workerId;
    }

    @Override
    public String getTrace() {
        return trace;
    }

    @Override
    public void setTrace(String trace) {
        this.trace = trace;
    }
}
