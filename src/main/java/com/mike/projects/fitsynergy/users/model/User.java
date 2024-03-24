package com.mike.projects.fitsynergy.users.model;

import com.mike.projects.fitsynergy.security.model.SystemUserDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @SequenceGenerator(sequenceName = "user_id_seq", name = "user_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_generator")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private Integer age;
    @Column(name = "gender")
    private String gender;
    @JoinColumn(name = "user_details_id")
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUserDetails userDetails;
}
