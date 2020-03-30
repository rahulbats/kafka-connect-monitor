package com.connect.models;


//@ManagedResource(objectName = "com.connect:name=ConnectorsStatus,type=Connectors")
public class ConnectorStatus implements ConnectorStatusMXbean {
    private String name;
    private String state;
    private String workerId;

    public ConnectorStatus(String name, String status, String workerId) {
        this.name=name;
        this.state=status;
        this.workerId = workerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String status) {
        this.state = status;
    }

    @Override
    public String getWorkerId() {
        return workerId;
    }

    @Override
    public void SetWorkerId(String workerId) {
        this.workerId = workerId;
    }


}
