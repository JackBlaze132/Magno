package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.domain.spi.IExternalUserProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ExternalUserProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ExternalUserProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IExternalUserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class ExternalUserProfileJpaAdapter implements IExternalUserProfilePersistencePort {

    private final IExternalUserProfileRepository externalUserProfileRepository;
    private final ExternalUserProfileEntityMapper externalUserProfileEntityMapper;

    @Override
    public Optional<ExternalUserProfile> findById(Long id) {
        Optional<ExternalUserProfileEntity> externalUserProfileEntity = externalUserProfileRepository.findById(id);
        return externalUserProfileEntity.map(externalUserProfileEntityMapper::toExternalUserProfile);
    }

    @Override
    public ExternalUserProfile save(ExternalUserProfile externalUserProfile) {
        ExternalUserProfileEntity externalUserProfileEntity =
                externalUserProfileEntityMapper.toExternalUserProfileEntity(externalUserProfile);
        ExternalUserProfileEntity savedExternalUserProfileEntity =
                externalUserProfileRepository.save(externalUserProfileEntity);
        return externalUserProfileEntityMapper.toExternalUserProfile(savedExternalUserProfileEntity);
    }

    @Override
    public ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile) {
        ExternalUserProfileEntity externalUserProfileEntity =
                externalUserProfileEntityMapper.toExternalUserProfileEntity(id, externalUserProfile);
        ExternalUserProfileEntity updatedExternalUserProfileEntity =
                externalUserProfileRepository.save(externalUserProfileEntity);
        return externalUserProfileEntityMapper.toExternalUserProfile(updatedExternalUserProfileEntity);
    }

    @Override
    public void deleteById(Long id) {
        externalUserProfileRepository.deleteById(id);
    }

    @Override
    public List<ExternalUserProfile> findAll() {
        return externalUserProfileEntityMapper.toExternalUserProfileList(externalUserProfileRepository.findAll());
    }

    @Override
    public List<ExternalUserProfile> findAllProfilesByUserId(Long userId) {
        return externalUserProfileEntityMapper.toExternalUserProfileList(
                externalUserProfileRepository.findAllByUser_Id(userId));
    }
}
