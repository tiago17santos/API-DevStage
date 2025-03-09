package com.techVerse.DevStage.Controllers;

import com.techVerse.DevStage.Dtos.ErrorMessage;
import com.techVerse.DevStage.Dtos.SubscriptionRankingItem;
import com.techVerse.DevStage.Dtos.SubscriptionResponse;
import com.techVerse.DevStage.Dtos.UserDto;
import com.techVerse.DevStage.Services.Exceptions.EventNotFoundException;
import com.techVerse.DevStage.Services.Exceptions.SubscriptionConflictException;
import com.techVerse.DevStage.Services.Exceptions.UserIndicationNotFound;
import com.techVerse.DevStage.Services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/{prettyName}", "/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody UserDto subscriber, @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse subs = subscriptionService.createNewSubscription(prettyName, subscriber, userId);

            if (subs != null) {

                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(subs.subscriptionNumber()).toUri();
                return ResponseEntity.created(uri).body(subs);
            }

        } catch (EventNotFoundException | UserIndicationNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
        } catch (SubscriptionConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(e.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{prettyName}/ranking")
    public ResponseEntity<?> generateRankingByEvent(@PathVariable String prettyName) {
        try {
            List<SubscriptionRankingItem> ranking = subscriptionService.getCompleteRanking(prettyName).subList(0, 3);
            return ResponseEntity.ok(ranking);

        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> generateRankingByUser(@PathVariable String prettyName, @PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(subscriptionService.getRankingByUser(prettyName, userId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
        }
    }
}
