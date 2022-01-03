package pl.krzesniak.gymapp.services;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzesniak.gymapp.entities.user.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String role_user);
}
