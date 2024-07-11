package com.sparta.kanbanboardproject.domain.card.service;

import com.sparta.kanbanboardproject.domain.card.dto.CardCreateRequestDto;
import com.sparta.kanbanboardproject.domain.card.dto.CardResponseDto;
import com.sparta.kanbanboardproject.domain.card.dto.CardUpdateRequestDto;
import com.sparta.kanbanboardproject.domain.card.repository.CardRepository;
import com.sparta.kanbanboardproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "CardService")
public class CardService {

    private final CardRepository cardRepository;

    public List<CardResponseDto> getAllByBoard(User user, Long boardId) {
        return null;
    }

    public CardResponseDto createCard(User user, Long boardId, Long progressId, CardCreateRequestDto requestDto) {
        return null;
    }

    public CardResponseDto updateCard(User user, Long boardId, Long progressId, Long cardId, CardUpdateRequestDto requestDto) {
        return null;
    }

    public void deleteCard(User user, Long boardId, Long progressId, Long cardId) {
    }

    public CardResponseDto updateCardSequence(User user, Long boardId, Long progressId, Long cardId, Long sequence) {
        return null;
    }

    public CardResponseDto getCardById(User user, Long boardId, Long progressId, Long cardId) {
        return null;
    }
}
