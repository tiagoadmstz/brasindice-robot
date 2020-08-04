package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.Novo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovoRepository extends JpaRepository<Novo, String> {

    List<Novo> findByEan(String codigoEan);

    List<Novo> findByTiss(String codigoTiss);

    List<Novo> findByTuss(String codigoTuss);

}
