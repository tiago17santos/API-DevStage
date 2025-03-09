package com.techVerse.DevStage.controllers;

import com.techVerse.DevStage.Dtos.SubscriptionDto;
import com.techVerse.DevStage.Dtos.UserDto;
import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Entities.Subscription;
import com.techVerse.DevStage.Entities.SubscriptionWrapper;
import com.techVerse.DevStage.Entities.User;
import com.techVerse.DevStage.Repository.SubscriptionRepository;
import com.techVerse.DevStage.Repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SubscriptionControllerIntegrationTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Subscription subscription;

    @BeforeEach
    public void setUp() {
        // Limpar o banco de dados antes de cada teste
        subscriptionRepository.deleteAll();

        // Criar uma inscrição para teste
        subscription = getSubscription();
        subscriptionRepository.save(subscription);
    }

    private static Event getEventTest() {
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

    public Subscription getSubscription() {
        Event event = getEventTest();

        UserDto userDto = new UserDto("User Test", "user@gmail.com");

        User subscriber = new User();
        subscriber.setUserName(userDto.getUserName());
        subscriber.setUserEmail(userDto.getUserEmail());

        userRepository.save(subscriber);


        return new Subscription(event, subscriber, null);
    }

    @Test
    public void testCreateSubscription() {
        String url = "http://localhost:" + port + "/subscription/" + subscription.getEvent().getPrettyName();

        ResponseEntity<SubscriptionDto> response = restTemplate.postForEntity(url, subscription, SubscriptionDto.class);

        // Verifica se a resposta HTTP foi 201 (Criado)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Recuperando o id da inscrição do DTO retornado pela resposta
        int subscriptionId = response.getBody().getSubscriptionNumber();

        // Verifica se o id da inscrição não é nulo, indicando que foi criada com sucesso
        assertNotNull(subscriptionId);

        // Consulta o banco de dados para garantir que a inscrição foi persistida
        Subscription persistedSubscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new RuntimeException("Subscription not found"));

        // Verifica se o evento foi corretamente associado à inscrição e se o usuário subscritor foi salvo
        assertEquals("User Test", persistedSubscription.getSubscriber().getUserName());
        assertEquals("Indication User", persistedSubscription.getIndication().getUserName());

        // Verifica os dados do evento
        assertEquals("Evento Teste", persistedSubscription.getEvent().getTitle());
    }

    @Test
    public void testCreateSubscriptionFailure() {
        String url = "http://localhost:" + port + "/subscription/" + subscription.getEvent().getPrettyName();

        ResponseEntity<SubscriptionDto> response = restTemplate.postForEntity(url, subscription, SubscriptionDto.class);

        // Verifica se a resposta HTTP foi 409 (requisição não pode ser processado devido a um conflito com o estado atual do servidor)
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void generateRankingByEvent() {
        String url = "http://localhost:" + port + "/ranking/event/" + subscription.getEvent().getPrettyName() + "/ranking";

        // Alterando o tipo de resposta para SubscriptionWrapper
        ResponseEntity<SubscriptionWrapper> response = restTemplate.getForEntity(url, SubscriptionWrapper.class);

        // Verifica se a resposta HTTP foi 200 (requisição foi bem-sucedida)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a resposta contém uma lista de subscriptions
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getSubscriptions());
        assertFalse(response.getBody().getSubscriptions().isEmpty()); // verificar se a lista não está vazia
    }

    @Test
    public void generateRankingByEventEmpty() {
        String url = "http://localhost:" + port + "/ranking/event/" + subscription.getEvent().getPrettyName() + "/ranking";

        // Alterando o tipo de resposta para SubscriptionWrapper para pegar a lista de inscrições no retorno
        ResponseEntity<SubscriptionWrapper> response = restTemplate.getForEntity(url, SubscriptionWrapper.class);

        // Verifica se a resposta HTTP foi 404 (não foi encontrado)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertNotNull(response.getBody());
        assertNull(response.getBody().getSubscriptions()); // Garante que a lista de subscriptions é nula

    }

    @Test
    public void generateRankingByUser() {
        String url = "http://localhost:" + port + "/ranking/event/" + subscription.getEvent().getPrettyName() + "/ranking/" + subscription.getSubscriber().getUserId();

        // Alterando o tipo de resposta para SubscriptionWrapper para pegar a lista de inscrições no retorno
        ResponseEntity<SubscriptionWrapper> response = restTemplate.getForEntity(url, SubscriptionWrapper.class);

        // Verifica se a resposta HTTP foi 200 (requisição foi bem-sucedida)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getSubscriptions());
        assertFalse(response.getBody().getSubscriptions().isEmpty()); // verificar se a lista não está vazia

    }

    @Test
    public void generateRankingByUserEmpty() {
        String url = "http://localhost:" + port + "/ranking/event/" + subscription.getEvent().getPrettyName() + "/ranking/" + subscription.getSubscriber().getUserId();

        // Alterando o tipo de resposta para SubscriptionWrapper para pegar a lista de inscrições no retorno
        ResponseEntity<SubscriptionWrapper> response = restTemplate.getForEntity(url, SubscriptionWrapper.class);

        // Verifica se a resposta HTTP foi 404 (não foi encontrado)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertNotNull(response.getBody());
        assertNull(response.getBody().getSubscriptions()); // Garante que a lista de subscriptions é nula
    }
}
