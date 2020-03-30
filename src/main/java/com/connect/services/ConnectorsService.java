package com.connect.services;

import com.connect.models.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.*;
import javax.management.openmbean.*;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConnectorsService {
    final Log logger = LogFactory.getLog(getClass());
    @Value("${connect.url}")
    private String connectURL;

    @Value("${alert.enabled}")
    private boolean alertEnabled;

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

    public ConnectorContainer getConnectorForState( String statesString) throws OpenDataException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ConnectorContainer connectorContainer = new ConnectorContainer();
        connectorContainer.setStates(statesString);
        RestTemplate restTemplate = new RestTemplate();
        List<String> states = Arrays.asList(statesString.split(",")) ;
        String[] connectors = getConnectors();


        final OpenType<?>[] taskTypes = new OpenType<?>[3];
        taskTypes[0]=SimpleType.STRING;
        taskTypes[1]=SimpleType.STRING;
        taskTypes[2]=SimpleType.STRING;



        CompositeType taskCompositType = new CompositeType("task", "task detail",
                                                            new String[]{"id","status","trace"}, new String[]{"task id","task status","error trace"},
                                                            taskTypes);
        TabularType taskTabularType = new TabularType("tasks", "tasks array", taskCompositType, new String[]{"id","status","trace"});


        final OpenType<?>[] types = new OpenType<?>[3];
        types[0]=SimpleType.STRING;
        types[1]=SimpleType.STRING;
        types[2]=taskTabularType;


        CompositeType connectorType = new CompositeType("connector","connector detail",
                                                new String[]{"name","status","tasks"},
                                                new String[]{"connector name","connector status","connector tasks"},
                                                types);
        final TabularType type = new TabularType("connectors", "connectors array", connectorType, new String[]{"name","status"});
        final TabularDataSupport data = new TabularDataSupport(type);

        List<Connector> connectorList =     Arrays.stream(connectors)
                .map(connectorName->getConnector(connectorName)).collect(Collectors.toList());

        Supplier<Stream<Connector>> streamSupplier = () -> connectorList.stream();

        streamSupplier.get().forEach(connector -> {


                    final CompositeData line;
                    try {
                        ConnectorStatus connectorMbeanStatus = new ConnectorStatus(connector.getName(), connector.getState(), connector.getWorkerId());
                        StandardMBean mbean = new StandardMBean(connectorMbeanStatus, ConnectorStatusMXbean.class);
                        ObjectName objectName = new ObjectName( "com.connect:type=status,name="+connector.getName());
                        try {
                            mBeanServer.registerMBean(mbean, objectName );
                        } catch (InstanceAlreadyExistsException ex) {
                            mBeanServer.unregisterMBean(objectName);
                            mBeanServer.registerMBean(mbean, objectName);
                        }
                        connector.getTasks().forEach(task -> {
                            ConnectorTaskStatus connectorTaskStatus = new ConnectorTaskStatus(task.getId(), task.getState(), task.getWorkerId(), task.getTrace());
                            ObjectName objectNameForTask = null;
                            try {
                                objectNameForTask = new ObjectName( "com.connect.task:type=status,connector="+connector.getName()+",task="+task.getId());
                                StandardMBean connectorTaskMbean = new StandardMBean(connectorTaskStatus, ConnectorTaskStatusMXBean.class);

                                try {

                                    mBeanServer.registerMBean(connectorTaskMbean, objectNameForTask );
                                } catch (InstanceAlreadyExistsException ex) {
                                    mBeanServer.unregisterMBean(objectNameForTask);
                                    mBeanServer.registerMBean(connectorTaskMbean, objectNameForTask);
                                } catch (NotCompliantMBeanException e) {
                                    logger.error(e);
                                } catch (MBeanRegistrationException e) {
                                    logger.error(e);
                                }
                            } catch (MalformedObjectNameException | InstanceNotFoundException | MBeanRegistrationException | InstanceAlreadyExistsException | NotCompliantMBeanException e) {
                                logger.error(e);
                            }

                        });

                    } catch ( MalformedObjectNameException e) {
                        logger.error(e);
                    } catch (NotCompliantMBeanException e) {
                        logger.error(e);
                    } catch (InstanceAlreadyExistsException e) {
                        logger.error(e);
                    } catch (MBeanRegistrationException | InstanceNotFoundException e) {
                        logger.error(e);
                    }

                });

        if(alertEnabled) {
            List<Connector> filteredConnectors = streamSupplier.get().filter(connector -> {
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
        }

        //logger.info(data);
        return connectorContainer;
    }

}
