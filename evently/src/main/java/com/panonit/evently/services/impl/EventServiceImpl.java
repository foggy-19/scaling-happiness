package com.panonit.evently.services.impl;

import com.panonit.evently.domain.EventStatus;
import com.panonit.evently.domain.dtos.*;
import com.panonit.evently.domain.entities.Event;
import com.panonit.evently.domain.entities.TicketType;
import com.panonit.evently.domain.entities.User;
import com.panonit.evently.exceptions.EventNotFoundException;
import com.panonit.evently.exceptions.EventUpdateException;
import com.panonit.evently.exceptions.UserNotFoundException;
import com.panonit.evently.mapper.EventMapper;
import com.panonit.evently.repositories.EventRepository;
import com.panonit.evently.repositories.UserRepository;
import com.panonit.evently.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper mapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CreateEventResponseDto createEventForOrganizer(UUID organizerId, CreateEventRequestDto request) {
        log.info("Creating event for organizer id {}", organizerId);

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found", organizerId)));

        Event event = new Event();

        List<TicketType> ticketTypes = request.getTicketTypes().stream().map(ctt -> {
            TicketType tt = new TicketType();
            tt.setName(ctt.getName());
            tt.setPrice(ctt.getPrice());
            tt.setDescription(ctt.getDescription());
            tt.setAvailable(ctt.getTotalAvailable());
            tt.setEvent(event);

            return tt;
        }).toList();

        event.setName(request.getName());
        event.setVenue(request.getVenue());
        event.setStart(request.getStart());
        event.setEnd(request.getEnd());
        event.setSalesStart(request.getSalesStart());
        event.setSalesEnd(request.getSalesEnd());
        event.setStatus(request.getStatus());
        event.setTicketTypes(ticketTypes);
        event.setOrganizer(organizer);

        return mapper.toCreateEventResponseDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public UpdateEventResponseDto updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequestDto request) {
        log.info("Updating event for organizer id {}", organizerId);

        if (request.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if (!request.getId().equals(id)) {
            throw new EventUpdateException("Cannot update the ID of an event");
        }

        Event event = eventRepository.findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(String.format("Event with ID %s not found", id)));

        event.setName(request.getName());
        event.setVenue(request.getVenue());
        event.setStart(request.getStart());
        event.setEnd(request.getEnd());
        event.setSalesStart(request.getSalesStart());
        event.setSalesEnd(request.getSalesEnd());
        event.setStatus(request.getStatus());

        List<TicketType> ticketTypes = request.getTicketTypes().stream().map(ctt -> {
            TicketType tt = new TicketType();
            tt.setName(ctt.getName());
            tt.setPrice(ctt.getPrice());
            tt.setDescription(ctt.getDescription());
            tt.setAvailable(ctt.getTotalAvailable());
            tt.setEvent(event);

            return tt;
        }).toList();
        event.getTicketTypes().clear();
        event.getTicketTypes().addAll(ticketTypes);

        return mapper.toUpdateEventResponseDto(eventRepository.save(event));
    }

    @Override
    public Page<ListEventResponseDto> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        log.info("Listing events for organizer ID {}", organizerId);

        return eventRepository.findByOrganizerId(organizerId, pageable).map(mapper::toListEventResponseDto);
    }

    @Override
    public Optional<GetEventResponseDto> getEventForOrganizer(UUID organizerId, UUID id) {
        log.info("Finding event for organizer ID {}", organizerId);

        return eventRepository.findByIdAndOrganizerId(id, organizerId).map(mapper::toGetEventResponseDto);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
        log.info("Deleting event for organizer ID {}", organizerId);

        eventRepository.findByIdAndOrganizerId(id, organizerId).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<ListPublishedEventResponseDto> listPublishedEvents(Pageable pageable) {
        log.info("Listing published events");

        return eventRepository.findByStatus(EventStatus.PUBLISHED, pageable).map(mapper::toListPublishedEventResponseDto);
    }

    @Override
    public Page<ListPublishedEventResponseDto> searchPublishedEvents(String query, Pageable pageable) {
        log.info("Searching published events for query {}", query);

        return eventRepository.searchEvents(query, pageable).map(mapper::toListPublishedEventResponseDto);
    }

    @Override
    public Optional<GetPublishedEventResponseDto> getPublishedEvent(UUID id) {
        log.info("Getting published event for ID {}", id);

        return eventRepository.findByIdAndStatus(id, EventStatus.PUBLISHED).map(mapper::toGetPublishedEventResponseDto);
    }
}
