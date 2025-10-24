package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.domain.spi.IActionLogPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ActionLogEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ActionLogEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IActionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
public class ActionLogJpaAdapter implements IActionLogPersistencePort {

    private final IActionLogRepository actionLogRepository;
    private final ActionLogEntityMapper actionLogEntityMapper;

    @Override
    public ActionLog save(ActionLog actionLog) {
        ActionLogEntity entity = actionLogEntityMapper.toEntity(actionLog);
        ActionLogEntity savedEntity = actionLogRepository.save(entity);
        return actionLogEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<ActionLog> findAll() {
        List<ActionLogEntity> entities = actionLogRepository.findAll();
        return actionLogEntityMapper.toDomainList(entities);
    }

    @Override
    public List<ActionLog> findByUserId(Long userId) {
        List<ActionLogEntity> entities = actionLogRepository.findByUserId(userId);
        return actionLogEntityMapper.toDomainList(entities);
    }

    @Override
    public List<ActionLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        List<ActionLogEntity> entities = actionLogRepository.findByTimestampBetween(start, end);
        return actionLogEntityMapper.toDomainList(entities);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        actionLogRepository.deleteByIds(ids);
    }

    @Override
    public List<ActionLog> getLogsOlderThanDays(LocalDateTime date) {
        List<ActionLogEntity> entities = actionLogRepository.findByTimestampBefore(date);
        return actionLogEntityMapper.toDomainList(entities);
    }
}

