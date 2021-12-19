package com.test.log.reviewer;

import com.test.log.reviewer.entity.EventEntity;
import com.test.log.reviewer.model.LogEvent;
import com.test.log.reviewer.service.BuildLogEvents;
import com.test.log.reviewer.service.PersistEventEntities;
import com.test.log.reviewer.service.ProcessLogFile;
import com.test.log.reviewer.service.parallel.ParallelProcessingEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LogReviewerApplication implements CommandLineRunner {

	public static final Logger LOGGER = LoggerFactory.getLogger(LogReviewerApplication.class);

	@Autowired
	private ProcessLogFile processLogFile;

	@Autowired
	private BuildLogEvents buildLogEvents;

	@Autowired
	private PersistEventEntities persistEventEntities ;

	@Autowired
	private ParallelProcessingEvents parallelProcessingEvents ;
	public static void main(String[] args) {
		LOGGER.debug("Starting the application");
		SpringApplication.run(LogReviewerApplication.class, args);
		LOGGER.debug("Completed the application");
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Started execution with args: {}", Arrays.toString(args));
		if(args.length>0) {

			if(args.length ==2 && "SINGLE".equalsIgnoreCase(args[1])) {
				List<LogEvent> logEvents = processLogFile.processLogFile(args[0]);
				LOGGER.info("Read Log File successfully, processing events");
				List<EventEntity> eventEntities = buildLogEvents.processLogEvents(logEvents);
				LOGGER.info("constructed event entities : {}", eventEntities.size());
				persistEventEntities.saveEvents(eventEntities);
				LOGGER.info("Completed Events persistence");
			}else{
				LOGGER.info("Processing log events parallely");
				parallelProcessingEvents.processLogEvents(args[0]);
				LOGGER.info("Completed Processing events");
				return;
			}
		}else {
			LOGGER.error("Please enter the Log file path");
		}
	}
}
