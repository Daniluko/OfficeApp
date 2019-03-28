package project.springData.service;

import project.databasesModel.Office;
import project.dto.OfficeRequest;

/**
 * Created by Danylo on 26.03.2019
 */
public interface OfficeCreator {
    Office createOffice(OfficeRequest officeRequest);
}
