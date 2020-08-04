package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long> {
}
