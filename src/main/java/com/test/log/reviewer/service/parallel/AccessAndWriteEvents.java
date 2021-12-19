package com.test.log.reviewer.service.parallel;

import com.test.log.reviewer.entity.EventEntity;
import com.test.log.reviewer.model.LogEvent;
import com.test.log.reviewer.repo.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

public class AccessAndWriteEvents implements Callable<Boolean> {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccessAndWriteEvents.class);

    private EventRepository eventRepository;

    private Set<LogEvent> events ;

    private LogEvent logEvent;

    public AccessAndWriteEvents(Set events, LogEvent logEvent,EventRepository eventRepository) {
        this.logEvent = logEvent;
        this.events = events;
        this.eventRepository = eventRepository;

    }

    @Override
    public Boolean call() {
        Optional<LogEvent> first = events.stream().filter(e -> e.equals(logEvent)).findFirst();
        if(!first.isPresent()){
            LOGGER.error("Didn't find the event on SET : Can't complete the Task",logEvent.getId());
            return Boolean.FALSE;
        }
        LOGGER.info("Processing and writing event with id: {}",first.get().getId());
        long duration = "STARTED".equalsIgnoreCase(first.get().getState())?
                logEvent.getTimestamp() - first.get().getTimestamp():first.get().getTimestamp() - logEvent.getTimestamp();
        String alert = duration > 4? "true" : " false" ;
        eventRepository.save(new EventEntity(duration,logEvent.getType(),logEvent.getHostname(),alert));
        LOGGER.info("############### DONE ################");
        return Boolean.TRUE;
    }
}
