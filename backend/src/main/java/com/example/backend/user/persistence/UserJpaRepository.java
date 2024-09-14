package com.example.backend.user.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    @Query(
            value = """
                select u.* from users u
                where lower(concat(u.first_name, ' ', u.last_name)) like lower(concat('%', :searchParam, '%'))
                or u.email like lower(concat('%', :searchParam, '%'))
            """,
            nativeQuery = true
    )
    Slice<UserEntity> search(@Param("searchParam") String searchParam, Pageable pageable);
}
