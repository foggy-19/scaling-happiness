package com.panonit.tasky.repositories;

import com.panonit.tasky.domain.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByTaskListId(UUID taskListId);

    Optional<TaskEntity> findByTaskListIdAndId(UUID taskListId, UUID taskId);

    void deleteByTaskListIdAndId(UUID taskListId, UUID taskId);
}
