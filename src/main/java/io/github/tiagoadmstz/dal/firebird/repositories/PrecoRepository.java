package io.github.tiagoadmstz.dal.firebird.repositories;

import io.github.tiagoadmstz.dal.firebird.models.Preco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecoRepository extends JpaRepository<Preco, String> {
}
