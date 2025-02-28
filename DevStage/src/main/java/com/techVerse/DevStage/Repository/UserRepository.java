package com.techVerse.DevStage.Repository;

import com.techVerse.DevStage.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUserEmail(String userEmail);
}
