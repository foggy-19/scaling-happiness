package com.panonit.tasky.repositories;

import com.panonit.tasky.domain.entities.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepository extends JpaRepository<TaskListEntity, UUID> {
}
