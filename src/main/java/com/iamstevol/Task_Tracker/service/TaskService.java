package com.iamstevol.Task_Tracker.service;

import com.iamstevol.Task_Tracker.dto.TaskRequestDto;
import com.iamstevol.Task_Tracker.enums.Status;
import com.iamstevol.Task_Tracker.exception.CustomException;
import com.iamstevol.Task_Tracker.model.Task;
import com.iamstevol.Task_Tracker.model.User;

import java.util.List;

public interface TaskService {

    Task createTask(User user, TaskRequestDto taskRequestDto);

    void deleteTask(Long taskId);

    void updateTask(String title, String description, Long taskId) throws CustomException;

    List<Task> findAllTask(Long userId);

    void moveTaskForward(Long taskId);

    void moveTaskBackward(Long taskId) throws CustomException;

    void moveTaskToDone(Long taskId);

    List<Task> getTasksByStatus(Status status) throws CustomException;
}
