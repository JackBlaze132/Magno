package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotVisibleException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileNotFoundException;
import com.unibague.magno.domain.exception.role.DiriRoleNotAllowedException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IFunctionaryProfilePersistencePort;

import java.util.List;

/**
 * Use case implementation for managing functionary profiles.
 * <p>
 * Handles business logic for functionary profile operations. Functionaries are
 * university staff members (professors, researchers, coordinators) who participate
 * in investigation groups and research seedbeds.
 * </p>
 * <p>
 * Business rules enforced:
 * <ul>
 *   <li>A user can only have one functionary profile per academic period</li>
 * </ul>
 * </p>
 */
public class FunctionaryProfileUseCase implements IFunctionaryProfileServicePort {

    private final IFunctionaryProfilePersistencePort functionaryProfilePersistencePort;
    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final IRoleServicePort roleServicePort;

    public FunctionaryProfileUseCase(IFunctionaryProfilePersistencePort functionaryProfilePersistencePort,
                                     IAcademicPeriodServicePort academicPeriodServicePort,
                                     IRoleServicePort roleServicePort) {
        this.functionaryProfilePersistencePort = functionaryProfilePersistencePort;
        this.academicPeriodServicePort = academicPeriodServicePort;
        this.roleServicePort = roleServicePort;
    }

    @Override
    public FunctionaryProfile findById(Long id) {
        return functionaryProfilePersistencePort.findById(id)
                .orElseThrow(() -> new FunctionaryProfileNotFoundException(
                        String.format("FunctionaryProfile with ID %d not found", id)
                ));
    }

    @Override
    public FunctionaryProfile save(FunctionaryProfile functionaryProfile) {
        Long userId = functionaryProfile.getUserId();
        Long academicPeriodId = functionaryProfile.getAcademicPeriodId();

        // Validate that the academic period is visible
        verifyAcademicPeriodIsVisible(academicPeriodId);

        // Validate that the role is not DIRI
        verifyRoleIsNotDiri(functionaryProfile.getRoleId());

        if (functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(userId, academicPeriodId)) {
            throw new FunctionaryProfileAlreadyExistsException(
                    String.format("FunctionaryProfile with user ID %d and academic period ID %d already exists",
                            userId, academicPeriodId)
            );
        }
        return functionaryProfilePersistencePort.save(functionaryProfile);
    }

    @Override
    public FunctionaryProfile saveIgnoringPeriodVisibility(FunctionaryProfile functionaryProfile) {
        Long userId = functionaryProfile.getUserId();
        Long academicPeriodId = functionaryProfile.getAcademicPeriodId();

        if (functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(userId, academicPeriodId)) {
            throw new FunctionaryProfileAlreadyExistsException(
                    String.format("FunctionaryProfile with user ID %d and academic period ID %d already exists",
                            userId, academicPeriodId)
            );
        }
        return functionaryProfilePersistencePort.save(functionaryProfile);
    }

    private void verifyAcademicPeriodIsVisible(Long academicPeriodId) {
        AcademicPeriod academicPeriod = academicPeriodServicePort.findById(academicPeriodId);
        if (!academicPeriod.isVisible()) {
            throw new AcademicPeriodNotVisibleException(
                    "No se permite crear perfiles de funcionario en períodos académicos que no son visibles"
            );
        }
    }

    private void verifyRoleIsNotDiri(Long roleId) {
        Role diriRole = roleServicePort.findByName(SeedbedRole.DIRI);
        if (diriRole.getId().equals(roleId)) {
            throw new DiriRoleNotAllowedException(
                    "No se permite crear perfiles de funcionario con rol DIRI a través de este método"
            );
        }
    }

    @Override
    public FunctionaryProfile update(Long id, FunctionaryProfile functionaryProfile) {
        if(functionaryProfilePersistencePort.findById(id).isEmpty()) {
            throw new FunctionaryProfileNotFoundException(
                    String.format("FunctionaryProfile with ID %d could not be updated because it does not exist", id)
            );
        }
        return functionaryProfilePersistencePort.update(id, functionaryProfile);
    }

    @Override
    public void deleteById(Long id) {
        if(functionaryProfilePersistencePort.findById(id).isEmpty()) {
            throw new FunctionaryProfileNotFoundException(
                    String.format("FunctionaryProfile with ID %d could not be deleted because it does not exist", id)
            );
        }
        functionaryProfilePersistencePort.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndAcademicPeriodId(Long userId, Long academicPeriodId) {
        return functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(userId, academicPeriodId);
    }

    @Override
    public List<FunctionaryProfile> findAll() {
        return functionaryProfilePersistencePort.findAll();
    }

    @Override
    public List<FunctionaryProfile> findAllProfilesByUserId(Long userId) {
        return functionaryProfilePersistencePort.findAllProfilesByUserId(userId);
    }

    @Override
    public List<FunctionaryProfile> findAllProfilesByAcademicPeriodId(Long academicPeriodId) {
        return functionaryProfilePersistencePort.findAllProfilesByAcademicPeriodId(academicPeriodId);
    }

    @Override
    public List<FunctionaryProfile> findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(Long functionaryProfileId, Long academicPeriodId) {
        return functionaryProfilePersistencePort
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(functionaryProfileId, academicPeriodId);
    }
}
