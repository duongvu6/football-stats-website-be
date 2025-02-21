package vn.ptit.project.epl_web.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MatchAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
}
