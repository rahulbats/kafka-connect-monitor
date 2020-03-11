package com.connect.services;

import com.connect.models.Connector;
import com.connect.models.ConnectorContainer;
import com.connect.models.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectorsService {
    final Log logger = LogFactory.getLog(getClass());
    @Value("${connect.url}")
    private String connectURL;

    public String[] getConnectors() {
        RestTemplate restTemplate = new RestTemplate();
        String[] connectors = restTemplate.getForObject(connectURL+"/connectors", String[].class);
        return connectors;
    }

    public Connector getConnector( String connectorName) {
        RestTemplate restTemplate = new RestTemplate();
        Connector connector = restTemplate.getForObject(connectURL+"/connectors/"+connectorName+"/status", Connector.class);
        return connector;
    }

    public ConnectorContainer getConnectorForState( String statesString) {
        ConnectorContainer connectorContainer = new ConnectorContainer();
        connectorContainer.setStates(statesString);
        RestTemplate restTemplate = new RestTemplate();
        List<String> states = Arrays.asList(statesString.split(",")) ;
        String[] connectors = getConnectors();
        List<Connector> filteredConnectors = Arrays.stream(connectors)
                .map(connectorName->getConnector(connectorName))
                .filter(connector -> {
                    boolean stateExists = states.contains(connector.getState());
                    if(!stateExists) {
                        List<Task> tasks = connector.getTasks();
                        for(Task task: tasks) {
                            stateExists = states.contains( task.getState());
                            if(stateExists)
                                break;
                        }
                    }
                    return stateExists;
                } ).collect(Collectors.toList());
        connectorContainer.setConnectors(filteredConnectors);
        return connectorContainer;
    }

}
