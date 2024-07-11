package com.sparta.kanbanboardproject.domain.user.repository;

import com.sparta.kanbanboardproject.domain.user.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}