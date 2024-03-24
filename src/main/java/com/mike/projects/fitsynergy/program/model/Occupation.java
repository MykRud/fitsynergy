package com.mike.projects.fitsynergy.program.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity(name = "occupation")
public class Occupation {
    @Id
    @SequenceGenerator(sequenceName = "occupation_id_seq", name = "occupation_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "occupation_id_seq_generator")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "occupation", cascade = CascadeType.ALL)
    private List<Program> programs;
}
