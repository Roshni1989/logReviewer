package com.test.log.reviewer.service.parallel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.log.reviewer.model.LogEvent;
import com.test.log.reviewer.repo.EventRepository;
import com.test.log.reviewer.service.ProcessLogFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class ParallelProcessingEvents {
    public static final Logger LOGGER = LoggerFactory.getLogger(ParallelProcessingEvents.class);
    private ExecutorService executor = Executors.newCachedThreadPool();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EventRepository eventRepository;

    public void processLogEvents(String filePath) throws IOException {
        Set<LogEvent> logEvents = new HashSet<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(json -> {
                LogEvent logEvent = null;
                try {
                    logEvent = mapper.readValue(json, LogEvent.class);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Failed to process JSON Record",e);
                }
                if(logEvents.contains(logEvent))
                {
                    LOGGER.info("Processing Events for ID {}",logEvent.getId());
                    executor.submit(new AccessAndWriteEvents(logEvents,logEvent,eventRepository));
                }else{
                    LOGGER.info("Found first event for Id {}",logEvent.getId());
                    logEvents.add(logEvent);
                }
            });
        }
        executor.shutdown();
        try {
            while (!executor.isTerminated()){
                LOGGER.info("Awaiting completion of tasks : ");
                Thread.sleep(2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
