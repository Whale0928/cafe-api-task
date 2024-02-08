package app.practice.cafeapitask.owner.repository;

import app.practice.cafeapitask.owner.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByPhoneNumber(String phoneNumber);
}
