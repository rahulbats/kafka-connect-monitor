package com.connect.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {


    public String getState() {
        return state;
    }

    public void setState(String status) {
        this.state = status;
    }

    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @JsonProperty("worker_id")
    private String workerId;

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    private  String trace;

}
