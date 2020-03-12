# Welcome to Kafka-Connect-Monitor(KCM)
Kafka connect is an awesome way to integrate your source and sink apps to Kafka. 

But Kafka connect does not provide any proactive alerting in case your connect task dies due to external failure.

KCM is a tool which monitors your Kafka connect cluster for any tasks which go down and sends out Email alerts for them.

You can configure KCM to send out the alerts for states (RUNNING, PAUSED or FAILED) that you are interested in.


### How to run?
You can run KCM in two ways

* Build your jar 
  * Build jar using `./gradlew clean build`
  * Export environment variables
  * Run jar using `java -jar  build/libs/kafka-connect-monitor-1.0.0.jar `
* Run the docker container 
    * run the docker container using this command `docker run -e CONNECT_URL=[CONNECT URL] -e SMTP_HOST=[SMTP HOST] -e SMTP_PORT=[SMTP PORT] -e SMTP_TO=[TO ADDRESS] -e SMTP_FROM=[FROM ADDRESS] -e ROOT_URL=http://localhost:8080  -p 8080:8080  rahulbats/kafka-connect-monitor:LATEST_TAG`

### List of environment variables
#### Table below describes the environment variables. Refer the [application.properties file](src/main/resources/application.properties) on how these are used.
| Environment Variable | Optional | Description |
| --- | --- | --- |
| CONNECT_URL | No | URL of the connect cluster |
| SMTP_HOST | No | SMTP host |
| SMTP_PORT | No | SMTP port |
| SMTP_TO | No | To email address |
| ROOT_URL | Yes | URL of this app so that links from email direct to the status page |
| FREQUENCY | Yes | Frequency in milliseconds of alerts. Default is 300000 (5 minutes) |

  