package co.uk.apg.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String eventRef;

    @Column(columnDefinition = "VARCHAR(500) DEFAULT NULL")
    private String desc1;

    @Column(columnDefinition = "VARCHAR(500) DEFAULT NULL")
    private String desc2;

    @Column(columnDefinition = "VARCHAR(500) DEFAULT NULL")
    private String desc3;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private OffsetDateTime start;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private OffsetDateTime end;

    @Column(columnDefinition = "VARCHAR(2000) DEFAULT NULL")
    private String teacherName;

    @Column(columnDefinition = "VARCHAR(500) DEFAULT NULL")
    private String teacherEmail;

    @Column(columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String locCode;

    @Column(columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String locAdd1;

    @Column(columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String locAdd2;
}
