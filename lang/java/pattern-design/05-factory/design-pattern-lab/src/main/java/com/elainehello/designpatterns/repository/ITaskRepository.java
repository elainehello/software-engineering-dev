package com.elainehello.designpatterns.repository;

import com.elainehello.designpatterns.model.Task;
import java.util.Optional;
import java.util.List;

public interface ITaskRepository {

    Task save(Task task);

    // camelCase method
    Optional<Task> findById(Long id);

    List<Task> findAll();
}
