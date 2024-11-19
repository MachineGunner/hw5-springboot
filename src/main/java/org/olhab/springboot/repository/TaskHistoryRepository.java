package org.olhab.springboot.repository;

import org.olhab.springboot.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    @Query(value = "SELECT * FROM task_history WHERE todo_id = :todoId", nativeQuery = true)
    List<TaskHistory> findByTodoId(@Param("todoId") Long todoId);
}
