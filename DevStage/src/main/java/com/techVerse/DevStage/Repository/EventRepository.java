package com.techVerse.DevStage.Repository;

import com.techVerse.DevStage.Entities.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
