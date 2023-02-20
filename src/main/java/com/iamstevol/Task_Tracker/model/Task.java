package com.iamstevol.Task_Tracker.model;

import com.iamstevol.Task_Tracker.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(unique = true, nullable = false)
    private String title;
    private String description;

    @CreationTimestamp
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private LocalDateTime timeCompleted;

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return taskId != null && Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
