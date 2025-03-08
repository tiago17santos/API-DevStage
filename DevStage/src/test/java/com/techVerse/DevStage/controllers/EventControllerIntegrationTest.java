package com.techVerse.DevStage.controllers;


import com.techVerse.DevStage.Dtos.EventDto;
import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EventControllerIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Event event;

    @BeforeEach
    public void setUp() {
        // Limpar o banco de dados antes de cada teste
        eventRepository.deleteAll();

        // Criar um evento para teste
        event = getEvent();
        eventRepository.save(event);
    }

    private static Event getEvent() {
        String startDataString = "2025-03-06";
        String endDataString = "2025-03-16";
        String startTimeString = "19:00:00";
        String endTimeString = "21:00:00";

        LocalDate startDate = LocalDate.parse(startDataString);
        LocalDate endDate = LocalDate.parse(endDataString);
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);

        return new Event("Evento Teste", "evento-teste", "Online", 10.5, startDate, endDate, startTime, endTime);
    }

    @Test
    public void testSaveEvent() {
        String url = "http://localhost:" + port + "/events";

        // Realizando o POST no endpoint /eventos para cadastrar o evento
        ResponseEntity<EventDto> response = restTemplate.postForEntity(url, event, EventDto.class);

        // Verifica se a resposta HTTP foi 201 (Criado)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica se o evento foi salvo no banco
        Event savedEvent = eventRepository.findByPrettyName("evento-teste");
        assertNotNull(savedEvent);
        assertEquals("Evento Teste", savedEvent.getTitle());
    }

    @Test
    public void testGetEventByPrettyName_ValidPrettyName() {
        String url = "http://localhost:" + port + "/events/" + event.getPrettyName();

        // Realizando o GET no endpoint /eventos/{pretty_name} para buscar o evento especifico
        ResponseEntity<EventDto> response = restTemplate.getForEntity(url, EventDto.class);

        // Verifica se a resposta HTTP foi 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(event.getPrettyName(), response.getBody().getPrettyName());

    }

    @Test
    public void testGetEventByPrettyName_InvalidPrettyName() {
        String url = "http://localhost:" + port + "/events/evento-nao-existente";

        // Realizando o GET no endpoint /eventos/{pretty_name} para buscar o evento especifico
        ResponseEntity<EventDto> response = restTemplate.getForEntity(url, EventDto.class);

        // Verifica se a resposta HTTP foi 404 (NOT_FOUND)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


}
