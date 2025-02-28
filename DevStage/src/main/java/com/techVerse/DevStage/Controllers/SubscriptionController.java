package com.techVerse.DevStage.Controllers;

import com.techVerse.DevStage.Dtos.SubscriptionDto;
import com.techVerse.DevStage.Dtos.UserDto;
import com.techVerse.DevStage.Entities.User;
import com.techVerse.DevStage.Services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{prettyName}")
    public ResponseEntity<SubscriptionDto> createSubscription(@PathVariable String prettyName, @RequestBody UserDto subscriber ) {
        SubscriptionDto subs = subscriptionService.createNewSubscription(prettyName, subscriber);

        if( subs == null ) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subs);



    }
}
