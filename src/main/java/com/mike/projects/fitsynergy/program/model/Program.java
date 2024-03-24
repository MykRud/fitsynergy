package com.mike.projects.fitsynergy.program.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity(name = "program")
@NoArgsConstructor
public class Program implements Cloneable{
    @Id
    @SequenceGenerator(sequenceName = "program_id_seq", name = "program_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "program_id_seq_generator")
    private Integer id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "occupation_id")
    @ManyToOne
    private Occupation occupation;

    @JoinTable(
            name = "program_exercise",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ExerciseContext> exercises;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "is_taken")
    private Boolean taken;

    public Program(String name, Occupation occupation,Level level, Boolean taken) {
        this.name = name;
        this.occupation = occupation;
        this.level = level;
        this.taken = taken;
    }

    @Override
    public Program clone() throws CloneNotSupportedException {
        Program cloneProgram = new Program(this.getName(), this.getOccupation(), this.getLevel(), this.taken);
        cloneProgram.setExercises(this.getExercises().stream()
                .map(e -> {
                    try{
                        return e.clone();
                    } catch (CloneNotSupportedException ex){
                        throw new RuntimeException("Cannot clone exercise " + e.getId());
                    }
                })
                .toList());
        return cloneProgram;
    }
}
