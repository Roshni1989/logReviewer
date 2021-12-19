package com.test.log.reviewer.repo;

import com.test.log.reviewer.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<EventEntity, Long> {
}
