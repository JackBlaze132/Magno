package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.domain.spi.IErrorLogPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ErrorLogEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ErrorLogEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
public class ErrorLogJpaAdapter implements IErrorLogPersistencePort {

    private final IErrorLogRepository errorLogRepository;
    private final ErrorLogEntityMapper errorLogEntityMapper;

    @Override
    public ErrorLog save(ErrorLog errorLog) {
        ErrorLogEntity entity = errorLogEntityMapper.toEntity(errorLog);
        ErrorLogEntity savedEntity = errorLogRepository.save(entity);
        return errorLogEntityMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteLogsOlderThanDays(List<Long> ids) {
        errorLogRepository.deleteByIds(ids);
    }

    @Override
    public List<ErrorLog> getLogsOlderThanDays(LocalDateTime localDateTime) {
        return errorLogEntityMapper.toDomainList(
                errorLogRepository.findByTimestampBefore(localDateTime)
        );
    }
}

