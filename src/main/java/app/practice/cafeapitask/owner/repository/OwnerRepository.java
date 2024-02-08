package app.practice.cafeapitask.owner.repository;

import app.practice.cafeapitask.owner.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
