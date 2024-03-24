package com.mike.projects.fitsynergy.users.model;

import com.mike.projects.fitsynergy.program.model.ProgramClient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@PrimaryKeyJoinColumn(name = "client_id")
public class Client extends User{
//    @SequenceGenerator(sequenceName = "client_id_seq", name = "client_id_seq_generator", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_seq_generator")
//    @Column(name = "client_id")
//    private Integer clientId;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ProgramClient> takenPrograms;
}
