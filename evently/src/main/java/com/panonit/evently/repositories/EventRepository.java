package com.panonit.evently.repositories;

import com.panonit.evently.domain.EventStatus;
import com.panonit.evently.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

    Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);

    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    Optional<Event> findByIdAndStatus(UUID id,  EventStatus status);

    @Query(value = "SELECT * FROM events WHERE status = 'PUBLISHED' AND " +
            "(to_tsvector('english', name) @@ plainto_tsquery('english', :searchTerm || ':*') OR " +
            "to_tsvector('english', venue) @@ plainto_tsquery('english', :searchTerm || ':*'))", nativeQuery = true)
    Page<Event> searchEvents(@Param("searchTerm") String searchTerm, Pageable pageable);
}
