package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.MedicamentoSubstancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoSubstanciaRepository extends JpaRepository<MedicamentoSubstancia, String> {
}
