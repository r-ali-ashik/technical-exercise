package co.uk.apg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String eventRef;
    private String desc1;
    private String desc2;
    private String desc3;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private String teacherName;
    private String teacherEmail;
    private String locCode;
    private String locAdd1;
    private String locAdd2;
}
