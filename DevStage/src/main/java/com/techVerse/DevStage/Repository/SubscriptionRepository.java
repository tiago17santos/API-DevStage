package com.techVerse.DevStage.Repository;

import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Entities.Subscription;
import com.techVerse.DevStage.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);
}
