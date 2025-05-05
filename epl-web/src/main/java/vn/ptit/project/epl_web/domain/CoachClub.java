package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "coach_club")
@Getter
@Setter
public class CoachClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="head_coach_id")
    private HeadCoach headCoach;
    @ManyToOne
    @JoinColumn(name="club_id", nullable = true)
    private Club club;
    private LocalDate startDate;
    private LocalDate endDate;
}
