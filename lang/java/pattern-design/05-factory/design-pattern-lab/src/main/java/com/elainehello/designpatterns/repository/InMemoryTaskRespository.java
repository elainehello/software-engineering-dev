package com.elainehello.designpatterns.repository;

import com.elainehello.designpatterns.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRespository implements ITaskRepository{

    private final ConcurrentMap<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        Long id = task.getId();

        if (id == null) {
            id = sequence.incrementAndGet();

            task = new Task(
                    id,
                    task.getTitle(),
                    task.getDescription()
            );
        }

        tasks.put(id, task);

        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }
}
