package co.uk.apg.service.impl;

import co.uk.apg.dto.EventDto;
import co.uk.apg.entity.Event;
import co.uk.apg.exception.ApiException;
import co.uk.apg.repository.EventRepository;
import co.uk.apg.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventDto> getEvents(String eventRef, String desc, String name, String email,
                                    String loc, LocalDate startDate, LocalDate endDate) {
        try {
            log.info("Request reach to the service layer, initiating data layer call for - ");
            log.info("eventRef: {} - desc: {} - name: {} - email: {}\nloc: {} - startDate: {} - endDate: {}", eventRef, desc, name, name, loc, startDate, endDate);

            List<Event> events = eventRepository.findEvents(eventRef, desc, name, email, loc, startDate, endDate);
            log.info("Total Event found: {}", events.size());

            log.info("Transforming Event int EventDto");
            return events.stream()
                    .map(event -> EventDto.builder()
                            .eventRef(event.getEventRef())
                            .desc1(event.getDesc1())
                            .desc2(event.getDesc2())
                            .desc3(event.getDesc3())
                            .start(event.getStart())
                            .end(event.getEnd())
                            .teacherName(event.getTeacherName())
                            .teacherEmail(event.getTeacherEmail())
                            .locCode(event.getLocCode())
                            .locAdd1(event.getLocAdd1())
                            .locAdd2(event.getLocAdd2())
                            .build())
                    .collect(Collectors.toList());
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException(ex);
        }
    }
}