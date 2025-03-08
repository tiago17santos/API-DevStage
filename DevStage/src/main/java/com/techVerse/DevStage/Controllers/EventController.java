package com.techVerse.DevStage.Controllers;

import com.techVerse.DevStage.Dtos.EventDto;
import com.techVerse.DevStage.Services.EventService;
import com.techVerse.DevStage.Services.Exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto> save(@RequestBody EventDto eventDto) {
        eventDto = eventService.saveEvent(eventDto);
        if (eventDto == null) {
            return ResponseEntity.notFound().build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(eventDto.getEventId()).toUri();
        return ResponseEntity.created(uri).body(eventDto);
    }

    @GetMapping("/{prettyName}")
    public ResponseEntity<EventDto> getEventByPrettyName(@PathVariable String prettyName) {
        try {
            EventDto eventDto = eventService.getEventByPrettyName(prettyName);
            return ResponseEntity.ok(eventDto);
        } catch (EventNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        if(events.size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }
}
