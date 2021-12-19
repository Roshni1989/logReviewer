package com.test.log.reviewer.service;

import com.test.log.reviewer.entity.EventEntity;
import com.test.log.reviewer.model.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuildLogEvents {
    public static final Logger LOGGER = LoggerFactory.getLogger(BuildLogEvents.class);

    public List<EventEntity> processLogEvents(List<LogEvent> logEvents){
        // construct STARTED and FINISHED events
        Map<String,Tuple> eventsMap = new HashMap<>();

        /*logEvents.stream().map(e -> eventsMap.containsKey(e.getId())?populateTuple(e,eventsMap.get(e.getId())):
                eventsMap.put(e.getId(),populateTuple(e,new Tuple())));*/
        for(LogEvent e: logEvents){
            if(eventsMap.containsKey(e.getId())){
                Tuple tuple = eventsMap.get(e.getId());
                populateTuple(e, tuple);
            }else{
                Tuple tuple = new Tuple();
                populateTuple(e,tuple);
                eventsMap.put(e.getId(),tuple);
            }
        }

        return eventsMap.entrySet().stream().map(entry -> new EventEntity(entry.getValue().getDuration(),
                entry.getValue().getType(),
                entry.getValue().getHost(),
                entry.getValue().getAlert())).collect(Collectors.toList());


    }

    private Tuple populateTuple(LogEvent e, Tuple tuple) {
        if("STARTED".equalsIgnoreCase(e.getState())){
            tuple.start = e;
        }else{
            tuple.finished = e;
        }
        return tuple;
    }

    private static class Tuple{
        LogEvent start;

        LogEvent finished;

        long getDuration(){
            return finished.getTimestamp() - start.getTimestamp() ;
        }

        String getAlert(){
            return getDuration()>4?"true":"false";
        }

        String getType(){
            return start.getType();
        }

        String getHost(){
            return start.getHostname();
        }

    }
}
