package com.dhbw.get2gether.backend.user.adapter.out;

import com.dhbw.get2gether.backend.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User getByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User getById(String id);

    Optional<User> findByEmail(String email);
}
