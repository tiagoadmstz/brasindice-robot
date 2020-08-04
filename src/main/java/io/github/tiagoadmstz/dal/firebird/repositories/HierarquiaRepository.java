package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.Hierarquia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HierarquiaRepository extends JpaRepository<Hierarquia, Long> {
}
