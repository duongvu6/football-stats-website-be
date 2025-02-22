package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private String password;
    private String role;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshtoken;
}