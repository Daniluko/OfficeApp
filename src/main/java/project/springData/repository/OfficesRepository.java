package project.springData.repository;

import project.databasesModel.Office;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Danylo on 16.03.2019
 */
@Repository
public interface OfficesRepository extends JpaRepository<Office , BigDecimal> {
    @Cacheable("officeCashed")
    List<Office> findFirstByTargetAfter(BigDecimal target);
}
