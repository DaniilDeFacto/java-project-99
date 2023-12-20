package hexlet.code.service;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::mapShow)
                .toList();
    }

    public TaskDTO findById(Long id) {
        var taskStatus = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        return taskMapper.mapShow(taskStatus);
    }

    public TaskDTO create(TaskCreateDTO taskData) {
        var task = taskMapper.mapCreate(taskData);
        taskRepository.save(task);
        return taskMapper.mapShow(task);
    }

    public TaskDTO update(TaskUpdateDTO taskData, Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        taskMapper.mapUpdate(taskData, task);
        taskRepository.save(task);
        return taskMapper.mapShow(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
