package com.techVerse.DevStage.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionNumber;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "subscribed_user_id")
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "indication_user_id", nullable = true)
    private User indication;


    public Subscription() {}

    public Subscription(int subscriptionNumber, Event event, User subscriber, User indication) {
        this.subscriptionNumber = subscriptionNumber;
        this.event = event;
        this.subscriber = subscriber;
        this.indication = indication;
    }

    public int getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(int subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
