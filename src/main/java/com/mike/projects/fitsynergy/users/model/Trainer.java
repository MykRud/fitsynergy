package com.mike.projects.fitsynergy.users.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@PrimaryKeyJoinColumn(name = "trainer_id")
public class Trainer extends User{
//    @SequenceGenerator(sequenceName = "trainer_id_seq", name = "trainer_id_seq_generator", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_id_seq_generator")
//    @Column(name = "trainer_id")
//    private Integer trainer_id;
}
