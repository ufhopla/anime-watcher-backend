package com.sutorimingu.no.sekai.repository;

import com.sutorimingu.no.sekai.model.ERole;
import com.sutorimingu.no.sekai.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ufhopla
 * on 06/08/2021.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
