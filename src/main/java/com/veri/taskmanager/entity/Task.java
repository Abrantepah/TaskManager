package com.veri.taskmanager.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    @Column(length = 2000)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;


    public enum Status {
        PENDING,
        COMPLETED
    }
}