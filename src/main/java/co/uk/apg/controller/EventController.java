package co.uk.apg.controller;

import co.uk.apg.dto.EventDto;
import co.uk.apg.dto.EventResponseDto;
import co.uk.apg.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "eventsapi")
@Tag(name = "Event Controller", description = "Event Controller")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Events API", description = "Get Events API - returns a list of events") // meta annotation for swagger
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EventResponseDto.class))})}) // meta annotation for swagger
    public ResponseEntity<EventResponseDto> getEvents(@RequestParam(required = false) String eventRef,
                                                      @RequestParam(required = false) String desc,
                                                      @RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String email,
                                                      @RequestParam(required = false) String loc,
                                                      @RequestParam(required = false)
                                                        @Parameter(example = "31/12/2024")
                                                        @Schema(type = "string", format = "date")
                                                        @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                                      @RequestParam(required = false)
                                                        @Parameter(example = "31/12/2024")
                                                        @Schema(type = "string", format = "date")
                                                        @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        log.info("Request reached to get-event-api");
        List<EventDto> events = eventService.getEvents(eventRef, desc, name, email, loc, startDate, endDate);

        log.info("Sending response to the client");
        return ResponseEntity.ok(EventResponseDto
                .builder()
                .events(events)
                .build());
    }
}
