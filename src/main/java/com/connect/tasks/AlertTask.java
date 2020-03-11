package com.connect.tasks;

import com.connect.models.Connector;
import com.connect.models.ConnectorContainer;
import com.connect.services.ConnectorsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class AlertTask {
    final Log logger = LogFactory.getLog(getClass());

    @Value("${connect.states}")
    private String states;

    @Value("${connect.url}")
    private String connectURL;

    @Value("${smtp.to}")
    private String smtpTo;

    @Value("${smtp.from}")
    private String smtpFrom;

    @Value("${root.url}")
    private String rootURL;

    @Autowired
    ConnectorsService connectorsService;

    @Autowired
    JavaMailSender mailSender;

    @Scheduled(fixedRateString = "${frequency}")
    public void submitAlert() throws JsonProcessingException {
        ConnectorContainer connectorContainer = connectorsService.getConnectorForState(states);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(smtpTo);
        message.setFrom(smtpFrom);
        message.setSubject("Alert on "+connectURL+" for states "+states);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        message.setText(mapper.writeValueAsString(connectorContainer.getConnectors())+"\n\n"+rootURL+"/connectors/state/"+states);

        mailSender.send(message);
    }
}
