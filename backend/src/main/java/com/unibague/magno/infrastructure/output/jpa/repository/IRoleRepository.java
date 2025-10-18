package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
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

    Optional<RoleEntity> findByName(SeedbedRole name);
    boolean existsByName(SeedbedRole name);
}
