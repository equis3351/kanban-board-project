package com.sparta.kanbanboardproject.domain.card.repository;

import com.sparta.kanbanboardproject.domain.card.entity.Card;
import com.sparta.kanbanboardproject.domain.progress.entity.Progress;

import java.util.List;
import java.util.Optional;

public interface CustomCardRepository {

    Long countByProgressId(Long progressId);
    List<Card> findAllByProgress(Progress progress);
    List<Card> findByProgressIdAndSequenceNumberGreaterThan(Long progressId, Long sequenceNum);
    List<Card> findByProgressIdOrderBySequenceNumber(Long progressId);
}
