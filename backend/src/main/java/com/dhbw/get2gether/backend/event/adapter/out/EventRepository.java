package com.dhbw.get2gether.backend.event.adapter.out;

import com.dhbw.get2gether.backend.event.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findEventsByParticipantIdsContains(String participantId);

    Optional<Event> findByInvitationLink(String invitationLink);

}
