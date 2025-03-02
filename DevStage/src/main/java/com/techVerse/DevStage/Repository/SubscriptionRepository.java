package com.techVerse.DevStage.Repository;

import com.techVerse.DevStage.Dtos.SubscriptionRankingItem;
import com.techVerse.DevStage.Entities.Event;
import com.techVerse.DevStage.Entities.Subscription;
import com.techVerse.DevStage.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = " select count(subscription_number) as quantidade, indication_user_id, user_name " +
            " from tbl_subscription inner join tbl_user " +
            " on indication_user_id = user_id " +
            " where indication_user_id is not null and event_id = :eventId " +
            " group by indication_user_id " +
            " order by quantidade desc; ", nativeQuery = true)
    List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
