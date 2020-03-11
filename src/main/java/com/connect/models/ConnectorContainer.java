package com.connect.models;

import java.util.List;

public class ConnectorContainer {


    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }

    private String states;
    private List<Connector> connectors;


}
