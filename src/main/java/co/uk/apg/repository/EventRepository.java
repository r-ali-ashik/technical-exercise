package co.uk.apg.repository;

import co.uk.apg.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "FROM Event e" +
            " WHERE (?1 IS NULL OR e.eventRef = ?1)" +
            " AND (?2 IS NULL OR e.desc1 LIKE %?2% OR e.desc2 LIKE %?2% OR e.desc3 LIKE %?2%)" +
            " AND (?3 IS NULL OR e.teacherName LIKE %?3%)" +
            " AND (?4 IS NULL OR e.teacherEmail LIKE %?4%)" +
            " AND (?5 IS NULL OR e.locCode LIKE %?5% OR e.locAdd1 LIKE %?5% or e.locAdd2 LIKE %?5%)" +
            " AND (?6 IS NULL OR e.start >= ?6)" +
            " AND (?7 IS NULL OR e.end <= ?7)")
    List<Event> findEvents(String eventRef, String desc, String name, String email, String loc, LocalDate startDate, LocalDate endDate);
}
