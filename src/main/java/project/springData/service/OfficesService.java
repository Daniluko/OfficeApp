package project.springData.service;

import project.databasesModel.Office;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Danylo on 16.03.2019
 */

public interface OfficesService {
    Set<Office> getAllOffice();

    Set<Office> findOfficeFirstByTargetAfter (BigDecimal target);

    Office findOfficeById(BigDecimal id);

    void insertOffice(Office office);

    void updateOffice(Office office);

    void deleteOffice(BigDecimal id);
}
