package com.techVerse.DevStage.Services;

import com.techVerse.DevStage.Dtos.EventDto;
import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventDto saveEvent(EventDto eventDto) {
        Event event = new Event();

        event.setTitle(eventDto.getTitle());
        event.setPrettyName(eventDto.getTitle().toLowerCase(Locale.ROOT).replace(" ", "-"));
        event.setLocation(eventDto.getLocation());
        event.setPrice(eventDto.getPrice());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());

        event = eventRepository.save(event);

        return new EventDto(event);
    }

    public List<EventDto> getAllEvents() {
        List<Event> event = (List<Event>) eventRepository.findAll();
        return event.stream().map(EventDto::new).collect(Collectors.toList());
    }

    public EventDto getEventByPrettyName(String prettyName) {
        Event event = eventRepository.findByPrettyName(prettyName);
        return new EventDto(event);
    }

}
