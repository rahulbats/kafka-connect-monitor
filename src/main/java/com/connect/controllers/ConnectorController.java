package com.connect.controllers;

import com.connect.models.Connector;
import com.connect.models.ConnectorContainer;
import com.connect.services.ConnectorsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.management.openmbean.OpenDataException;
import java.util.List;

@RestController
public class ConnectorController {
    @Autowired
    ConnectorsService connectorsService;


    @ApiOperation(value = "View the list of all connectors", response = String[].class)
    @GetMapping(path ="/connectors", produces = "application/json")
    public String[] getConnectors(){
       return connectorsService.getConnectors();
    }

    @ApiOperation(value = "Details of a single connector", response = Connector.class)
    @GetMapping(path ="/connectors/{connectorName}", produces = "application/json")
    public Connector getConnector( @PathVariable String connectorName){
        return connectorsService.getConnector(connectorName);
    }

    @ApiOperation(value = "Search for connectors matching the state. ", response = Connector.class)
    @GetMapping(path ="/connectors/state/{states}", produces = "application/json")
    public ConnectorContainer getConnectorsForState(@ApiParam(name = "states", value = "The state can be one of the states FAILED, RUNNING or PAUSED. You can pass multiple states coma seperated.", required = true, defaultValue = "FAILED")  @PathVariable String states) throws OpenDataException {
        return connectorsService.getConnectorForState(states);
    }
}
