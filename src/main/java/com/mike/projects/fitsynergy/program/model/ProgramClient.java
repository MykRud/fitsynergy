package com.mike.projects.fitsynergy.program.model;

import com.mike.projects.fitsynergy.users.model.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
@Entity(name = "program_client")
public class ProgramClient {
    @Id
    @SequenceGenerator(sequenceName = "program_user_id_seq", name = "program_user_id_seq_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "program_user_id_seq_generator")
    private Integer id;
    @JoinColumn(name = "program_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Program program;
    @JoinColumn(name = "client_id")
    @ManyToOne
    private Client client;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "complete_date")
    private Date completeDate;
    @Column(name = "is_completed")
    private Boolean completed;
}
