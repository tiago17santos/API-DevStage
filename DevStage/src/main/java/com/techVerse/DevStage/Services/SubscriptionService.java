package com.techVerse.DevStage.Services;

import com.techVerse.DevStage.Dtos.SubscriptionDto;
import com.techVerse.DevStage.Dtos.UserDto;
import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Entities.Subscription;
import com.techVerse.DevStage.Entities.User;
import com.techVerse.DevStage.Repository.EventRepository;
import com.techVerse.DevStage.Repository.SubscriptionRepository;
import com.techVerse.DevStage.Repository.UserRepository;
import com.techVerse.DevStage.Services.Exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionDto createNewSubscription(String eventName, UserDto subs) {

        User user = new User();
        user.setUserName(subs.getUserName());
        user.setUserEmail(subs.getUserEmail());
        user = userRepository.save(user);

        Event event = eventRepository.findByPrettyName(eventName);

        if (event == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(user);
        subscription = subscriptionRepository.save(subscription);

        return new SubscriptionDto(subscription);
    }
}
