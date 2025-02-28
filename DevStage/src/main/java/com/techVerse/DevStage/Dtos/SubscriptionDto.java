package com.techVerse.DevStage.Dtos;

import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Entities.Subscription;
import com.techVerse.DevStage.Entities.User;

public class SubscriptionDto {

    private int subscription_number;
    private Event evento;
    private User subscriber;
    private User indication;

    public SubscriptionDto() {
    }

    public SubscriptionDto(int subscription_number, Event evento, User subscriber, User indication) {
        this.subscription_number = subscription_number;
        this.evento = evento;
        this.subscriber = subscriber;
        this.indication = indication;
    }

    public SubscriptionDto(Subscription subscription) {
        subscription_number = subscription.getSubscriptionNumber();
        evento = subscription.getEvent();
        subscriber = subscription.getSubscriber();
        indication = subscription.getIndication();
    }

    public int getSubscriptionNumber() {
        return subscription_number;
    }

    public void setSubscriptionNumber(int subscription_number) {
        this.subscription_number = subscription_number;
    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {
        this.evento = evento;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getIndication() {
        return indication;
    }

    public void setIndication(User indication) {
        this.indication = indication;
    }
}
