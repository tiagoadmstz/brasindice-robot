package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.LaboratorioSubstancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratorioSubstanciaRepository extends JpaRepository<LaboratorioSubstancia, Long> {
}
