package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(value = """
        SELECT DISTINCT r.* FROM roles r
        JOIN functionary_profiles_roles fpr ON r.id = fpr.role_id
        JOIN functionary_profiles fp ON fpr.functionary_profile_id = fp.id
        WHERE fp.user_id = :userId
        UNION
        SELECT DISTINCT r.* FROM roles r
        JOIN student_profiles_roles spr ON r.id = spr.role_id
        JOIN student_profiles sp ON spr.student_profile_id = sp.id
        WHERE sp.user_id = :userId
        """, nativeQuery = true)
    List<RoleEntity> findAllRolesByUserId(@Param("userId") Long userId);
}
