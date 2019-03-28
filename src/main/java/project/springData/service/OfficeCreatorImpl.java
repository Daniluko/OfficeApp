package project.springData.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.databasesModel.Office;
import project.dto.OfficeDetails;
import project.dto.OfficeRequest;

/**
 * Created by Danylo on 26.03.2019
 */
@Service
public class OfficeCreatorImpl implements OfficeCreator {
    private static final Logger LOGGER  = LogManager.getLogger(OfficeCreatorImpl.class);

    @Override
    public Office createOffice(OfficeRequest officeRequest) {
        LOGGER.info("creat office , office request={}",officeRequest);
        Office result = new Office();
        result.setOffice(officeRequest.getOffice());
        result.setCity(officeRequest.getCity());
        result.setRegion(officeRequest.getRegion());

        OfficeDetails officeDetails = officeRequest.getOfficeDetails();
        result.setTarget(officeDetails.getTarget());
        result.setSales(officeDetails.getSales());
        LOGGER.info("createOffice, office, that was created after={}", result);

        return result;
    }
}
