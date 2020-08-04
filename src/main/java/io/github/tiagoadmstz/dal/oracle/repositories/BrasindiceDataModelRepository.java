package io.github.tiagoadmstz.dal.oracle.repositories;

import io.github.tiagoadmstz.dal.oracle.models.BrasindiceDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrasindiceDataModelRepository extends JpaRepository<BrasindiceDataModel, String> {
}
