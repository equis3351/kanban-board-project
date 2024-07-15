package com.sparta.kanbanboardproject.domain.user.repository.worker;

import com.sparta.kanbanboardproject.domain.user.entity.Worker;

import java.util.List;

public interface CustomWorkerRepository {

    List<Worker> findByUserId(Long userId);
}
