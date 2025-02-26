package com.visiblethread.docanalyzer.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
