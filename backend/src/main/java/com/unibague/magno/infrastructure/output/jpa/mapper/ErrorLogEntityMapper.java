package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.infrastructure.output.jpa.entity.ErrorLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ErrorLogEntityMapper {

    @Mapping(target = "userId", expression = "java(entity.getUser() != null ? entity.getUser().getId() : null)")
    ErrorLog toDomain(ErrorLogEntity entity);

    @Mapping(target = "user", ignore = true)
    ErrorLogEntity toEntity(ErrorLog domain);

    List<ErrorLog> toDomainList(List<ErrorLogEntity> entities);
}
