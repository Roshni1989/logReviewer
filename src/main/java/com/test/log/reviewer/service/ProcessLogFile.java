package com.test.log.reviewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.log.reviewer.model.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProcessLogFile {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProcessLogFile.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<LogEvent> processLogFile(String filePath) throws IOException {
        List<LogEvent> events = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(json-> {
                try {
                    events.add(mapper.readValue(json,LogEvent.class));
                } catch (JsonProcessingException e) {
                    LOGGER.error("Failed to process JSON Record",e);
                }
            });
        }
        LOGGER.info("Read JSON events: {}",events.size());
        return events;
    }
}
