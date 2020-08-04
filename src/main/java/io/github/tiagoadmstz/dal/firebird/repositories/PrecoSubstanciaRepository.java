package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.PrecoSubstancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecoSubstanciaRepository extends JpaRepository<PrecoSubstancia, String> {
}
