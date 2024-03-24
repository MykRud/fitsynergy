package com.mike.projects.fitsynergy.program.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "exercise")
public class Exercise {
    @Id
    @SequenceGenerator(sequenceName = "exercise_id_seq", name = "exercise_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_id_seq_generator")
    private Integer id;
    private String name;
    @Column(name = "video_link")
    private String videoLink;

}
