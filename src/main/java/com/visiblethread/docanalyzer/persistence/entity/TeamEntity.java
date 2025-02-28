package com.visiblethread.docanalyzer.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
