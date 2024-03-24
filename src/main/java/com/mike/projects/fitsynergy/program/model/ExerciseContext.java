package com.mike.projects.fitsynergy.program.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
@Entity(name = "exercise_context")
@NoArgsConstructor
public class ExerciseContext implements Cloneable {
    @Id
    @SequenceGenerator(sequenceName = "exercise_context_id_seq", name = "exercise_context_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_context_id_seq_generator")
    private Integer id;
    @JoinColumn(name = "exercise_id")
    @ManyToOne
    private Exercise exercise;
    @Column(name = "reps")
    private int reps;
    @Column(name = "sets")
    private int sets;
    @Column(name = "execution_time")
    private double executionTime;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "is_completed")
    private Boolean completed;

    public ExerciseContext(Exercise exercise, int reps, int sets, double executionTime, Date startDate, Boolean completed) {
        this.exercise = exercise;
        this.reps = reps;
        this.sets = sets;
        this.executionTime = executionTime;
        this.startDate = startDate;
        this.completed = completed;
    }

    @Override
    public ExerciseContext clone() throws CloneNotSupportedException {
        ExerciseContext exerciseContext = (ExerciseContext) super.clone();
        return new ExerciseContext(this.getExercise(), this.getReps(), this.getSets(), this.getExecutionTime(), this.getStartDate(), this.getCompleted());
    }
}
