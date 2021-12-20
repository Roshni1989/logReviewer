package com.test.log.reviewer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.log.reviewer.entity.EventEntity;
import com.test.log.reviewer.model.LogEvent;
import com.test.log.reviewer.service.BuildLogEvents;
import com.test.log.reviewer.service.ProcessLogFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class LogReviewerApplicationTests {

	@Autowired
	LogReviewerApplication logReviewerApplication ;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	ProcessLogFile processLogFile;

	@Autowired
	BuildLogEvents buildLogEvents ;
	@Test
	void contextLoads() {
	}

	@Test
	public void integrationTestMultiThreaded() {
		try {
			logReviewerApplication.run("../../logFile.txt");
		} catch (Exception e) {
			Assertions.assertEquals(Boolean.TRUE,true,"Failed to process Multithreaded Logfile processing");
		}
	}

	@Test
	public void integrationTestSingleThreadSmallFiles() {
		try {
			logReviewerApplication.run("../../logFile.txt","SINGLE");
		} catch (Exception e) {
			Assertions.assertEquals(Boolean.TRUE,true,"Failed to process Multithreaded Logfile processing");
		}
	}

	@Test
	public void testIndividualResponses(){
		try {
			List<LogEvent> events = processLogFile.processLogFile("../../logFile.txt");
			Assertions.assertEquals(10,events.size(),"Failed to process the Log file events, counts mismatched");
			List<EventEntity> eventEntities = buildLogEvents.processLogEvents(events);
			Assertions.assertEquals(5,eventEntities.size());
		} catch (IOException e) {
			Assertions.assertEquals(Boolean.TRUE,true,"Failed to process Logfile processing");
		}
	}


}
