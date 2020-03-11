package com.connect.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Connector {
    private String name;
    private String state;

    @JsonProperty("worker_id")
    private String workerId;

    private List<Task> tasks;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }




    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }



    public String getName() {
        return name;
    }

    public void setName(String connectorName) {
        this.name = connectorName;
    }



    @JsonProperty("connector")
    private void unpackNameFromNestedObject(Map<String, String> connector) {
        state = connector.get("state");
        workerId = connector.get("worker_id");
    }

    public String getState() {
        return state;
    }

    public void setState(String status) {
        this.state = status;
    }


}
