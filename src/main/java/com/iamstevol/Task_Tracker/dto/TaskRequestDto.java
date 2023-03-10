package com.iamstevol.Task_Tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRequestDto {

    private String title;

    private String description;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private LocalDateTime timeCompleted;

    public LocalDate getFormattedCreatedTime() {
        return LocalDate.parse(timeCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public LocalDate getFormattedUpdatedTime() {
        return LocalDate.parse(timeUpdated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    public LocalDate getFormattedCompletedTime() {
        return LocalDate.parse(timeCompleted.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
