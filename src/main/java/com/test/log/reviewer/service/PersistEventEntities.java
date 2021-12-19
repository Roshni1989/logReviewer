package com.test.log.reviewer.service;

import com.test.log.reviewer.entity.EventEntity;
import com.test.log.reviewer.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistEventEntities {

    @Autowired
    private EventRepository eventRepository;

    public void saveEvents(List<EventEntity> events){
        eventRepository.saveAll(events);
    }
}
