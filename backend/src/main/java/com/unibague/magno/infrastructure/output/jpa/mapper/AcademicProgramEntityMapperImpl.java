package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AcademicProgramEntityMapperImpl implements AcademicProgramEntityMapper {

    @Override
    public AcademicProgram toAcademicProgram(AcademicProgramEntity academicProgramEntity) {
        if ( academicProgramEntity == null ) {
            return null;
        }

        AcademicProgram academicProgram = new AcademicProgram();

        academicProgram.setId( academicProgramEntity.getId() );
        academicProgram.setName( academicProgramEntity.getName() );
        academicProgram.setProgramCode( academicProgramEntity.getProgramCode() );
        academicProgram.setType( academicProgramEntity.getType() );

        return academicProgram;
    }

    @Override
    public AcademicProgramEntity toAcademicProgramEntity(Long id, AcademicProgram academicProgram) {
        if ( id == null && academicProgram == null ) {
            return null;
        }

        AcademicProgramEntity academicProgramEntity = new AcademicProgramEntity();

        if ( academicProgram != null ) {
            academicProgramEntity.setName( academicProgram.getName() );
            academicProgramEntity.setProgramCode( academicProgram.getProgramCode() );
            academicProgramEntity.setType( academicProgram.getType() );
        }
        academicProgramEntity.setId( id );

        return academicProgramEntity;
    }

    @Override
    public AcademicProgramEntity toAcademicProgramEntity(AcademicProgram academicProgram) {
        if ( academicProgram == null ) {
            return null;
        }

        AcademicProgramEntity academicProgramEntity = new AcademicProgramEntity();

        academicProgramEntity.setId( academicProgram.getId() );
        academicProgramEntity.setName( academicProgram.getName() );
        academicProgramEntity.setProgramCode( academicProgram.getProgramCode() );
        academicProgramEntity.setType( academicProgram.getType() );

        return academicProgramEntity;
    }

    @Override
    public List<AcademicProgram> toAcademicProgramList(List<AcademicProgramEntity> academicProgramEntities) {
        return academicProgramEntities.stream()
                .map(this::toAcademicProgram)
                .toList();
    }

    @Override
    public AcademicProgramEntity toAcademicProgramEntity(IntegraAcademicProgram academicProgram) {
        AcademicProgramEntity academicProgramEntity = new AcademicProgramEntity();
        academicProgramEntity.setName(academicProgram.getProgramName());
        academicProgramEntity.setProgramCode(academicProgram.getProgramCode());
        academicProgramEntity.setType(AcademicProgramType.PREGRADO);
        return academicProgramEntity;
    }
}
