package com.connect.controllers;

import com.connect.models.Connector;
import com.connect.models.ConnectorContainer;
import com.connect.services.ConnectorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConnectorController {
    @Autowired
    ConnectorsService connectorsService;


    @GetMapping(path ="/connectors", produces = "application/json")
    public String[] getConnectors(){
       return connectorsService.getConnectors();
    }


    @GetMapping(path ="/connectors/{connectorName}", produces = "application/json")
    public Connector getConnector(@PathVariable String connectorName){
        return connectorsService.getConnector(connectorName);
    }

    @GetMapping(path ="/connectors/state/{states}", produces = "application/json")
    public ConnectorContainer getConnectorsForState(@PathVariable String states){
        return connectorsService.getConnectorForState(states);
    }
}
