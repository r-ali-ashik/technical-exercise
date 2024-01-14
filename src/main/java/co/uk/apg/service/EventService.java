package co.uk.apg.service;

import co.uk.apg.dto.EventDto;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    List<EventDto> getEvents(String eventRef, String desc, String name, String email, String loc, LocalDate startDate, LocalDate endDate);
}
