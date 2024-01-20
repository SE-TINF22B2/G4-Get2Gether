package com.dhbw.get2gether.backend.user.adapter.out;

import com.dhbw.get2gether.backend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User getByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    User getById(String id);
    Optional<User> findByEmail(String email);
}
