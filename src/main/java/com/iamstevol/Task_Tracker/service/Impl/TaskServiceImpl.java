package com.iamstevol.Task_Tracker.service.Impl;

import com.iamstevol.Task_Tracker.dto.TaskRequestDto;
import com.iamstevol.Task_Tracker.enums.Status;
import com.iamstevol.Task_Tracker.exception.CustomException;
import com.iamstevol.Task_Tracker.model.Task;
import com.iamstevol.Task_Tracker.model.User;
import com.iamstevol.Task_Tracker.repository.TaskRepository;
import com.iamstevol.Task_Tracker.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public Task createTask(User user, TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setTimeCreated(LocalDateTime.now());
        task.setStatus(Status.PENDING);
        task.setUser(user);
        taskRepository.save(task);
        return task;
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void updateTask(String title, String description, Long taskId) throws CustomException {
        taskRepository.updateTask(title, description, taskId);
        taskRepository.updateTime(LocalDateTime.now(), taskId);
    }

    @Override
    public List<Task> findAllTask(Long userId) {
        return taskRepository.findAllTaskByUserId(userId);
    }

    @Override
    public void moveTaskForward(Long taskId) {
        Optional<Task> found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.moveToProgress("IN_PROGRESS", taskId);
            taskRepository.updateTime(LocalDateTime.now(), taskId);
        }
    }

    @Override
    public void moveTaskBackward(Long taskId) {
        Optional<Task> found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.moveBackward("PENDING", taskId);
            taskRepository.updateTime(LocalDateTime.now(), taskId);
        }
    }

    @Override
    public void moveTaskToDone(Long taskId) {
        Optional<Task> found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.moveToComplete("COMPLETED", taskId);
            taskRepository.completedTime(LocalDateTime.now(), taskId);
        }
    }

    @Override
    public List<Task> getTasksByStatus(Status status) throws CustomException {
        User user = new User();
        List<Task> tasks = taskRepository.findAllTaskByUserId(user.getUserId());
        List<Task> pendingList = new ArrayList<>();
        List<Task> completedList = new ArrayList<>();
        List<Task> progressList = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(Status.PENDING)) {
                pendingList.add(task);
            } else if (task.getStatus().equals(Status.COMPLETED)) {
                completedList.add(task);
            } else if (task.getStatus().equals(Status.IN_PROGRESS)) {
                progressList.add(task);
            }
        }
        if (status == Status.PENDING) {
            return pendingList;
        } else if (status == Status.COMPLETED) {
            return completedList;
        } else if (status == Status.IN_PROGRESS) {
            return progressList;
        } else {
            throw new CustomException("The requested task is not available");
        }
    }

    public List<Task> findByPending(Long userId) {
        return taskRepository.findAllTaskByPending("PENDING", userId);
    }

    public List<Task> findByInProgress(Long userId) {
        return taskRepository.findAllTaskByInProgress("IN_PROGRESS", userId);
    }

    public List<Task> findByCompleted(Long userId) {
        return taskRepository.findAllTaskByCompleted("COMPLETED", userId);
    }
}
