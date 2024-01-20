package com.dhbw.get2gether.backend.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface Repo extends MongoRepository<User, String> {
    public User findByFirstName(String firstName) throws Exception;
    public List<User> findByLastName(String lastName);
    public Optional<User> findById(String id);
}
