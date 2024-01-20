package com.dhbw.get2gether.backend.event.adapter.out;

import com.dhbw.get2gether.backend.event.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findAllByParticipantIdsContaining(String participantIds);

}
