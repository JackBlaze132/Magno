package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link RoleEntity}.
 */
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Finds all roles assigned to a user across both functionary and student profiles.
     */
    @Query(value = """
        SELECT DISTINCT r.* FROM roles r
        JOIN functionary_profiles fp ON fp.role_id = r.id
        WHERE fp.user_id = :userId
        UNION
        SELECT DISTINCT r.* FROM roles r
        JOIN student_profiles sp ON sp.role_id = r.id
        WHERE sp.user_id = :userId
        """, nativeQuery = true)
    List<RoleEntity> findAllRolesByUserId(@Param("userId") Long userId);

    /**
     * Finds a role by its name.
     */
    Optional<RoleEntity> findByName(SeedbedRole name);

    /**
     * Checks if a role exists by its name.
     */
    boolean existsByName(SeedbedRole name);
}
