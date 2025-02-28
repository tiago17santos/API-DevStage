package com.techVerse.DevStage.Repository;

import com.techVerse.DevStage.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserEmail(String userEmail);
}
