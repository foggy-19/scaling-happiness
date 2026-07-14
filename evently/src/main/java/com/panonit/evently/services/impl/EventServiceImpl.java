package com.panonit.evently.services.impl;

import com.panonit.evently.domain.dtos.CreateEventRequestDto;
import com.panonit.evently.domain.dtos.CreateEventResponseDto;
import com.panonit.evently.domain.entities.Event;
import com.panonit.evently.domain.entities.TicketType;
import com.panonit.evently.domain.entities.User;
import com.panonit.evently.exceptions.UserNotFoundException;
import com.panonit.evently.mapper.EventMapper;
import com.panonit.evently.repositories.EventRepository;
import com.panonit.evently.repositories.UserRepository;
import com.panonit.evently.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper mapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CreateEventResponseDto createEvent(UUID organizerId, CreateEventRequestDto request) {
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
}
