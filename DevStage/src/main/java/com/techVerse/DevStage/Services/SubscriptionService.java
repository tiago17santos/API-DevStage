package com.techVerse.DevStage.Services;

import com.techVerse.DevStage.Dtos.SubscriptionResponse;
import com.techVerse.DevStage.Dtos.UserDto;
import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Entities.Subscription;
import com.techVerse.DevStage.Entities.User;
import com.techVerse.DevStage.Repository.EventRepository;
import com.techVerse.DevStage.Repository.SubscriptionRepository;
import com.techVerse.DevStage.Repository.UserRepository;
import com.techVerse.DevStage.Services.Exceptions.EventNotFoundException;
import com.techVerse.DevStage.Services.Exceptions.SubscriptionConflictException;
import com.techVerse.DevStage.Services.Exceptions.UserIndicationNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public SubscriptionResponse createNewSubscription(String eventName, UserDto subs, Integer userId) {

        User user = new User();
        user.setUserName(subs.getUserName());
        user.setUserEmail(subs.getUserEmail());
        List<User> users = userRepository.findByUserEmail(user.getUserEmail());
        if(users.isEmpty()){
            user = userRepository.save(user);
        }

        User indicador = userRepository.findById(userId).orElse(null);
        if(indicador == null){
            throw new UserIndicationNotFound("User " + userId + " not found");
        }

        Event event = eventRepository.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(user);
        subscription.setIndication(indicador);
        

        /*
        Event persistedEvent = eventRepository.findById(event.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        User persistedUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        */

        Subscription tmpSubscription = subscriptionRepository.findByEventAndSubscriber(event, user);
        if (tmpSubscription != null) {
            throw new SubscriptionConflictException("Subscription already exists for user " + user.getUserName() + " in event " + eventName);
        }

        subscription = subscriptionRepository.save(subscription);

        String link = "https://devstage.com/" + subscription.getEvent().getPrettyName() + "/" + subscription.getSubscriber().getUserId();

        return new SubscriptionResponse(subscription.getSubscriptionNumber(), link);
    }
}
