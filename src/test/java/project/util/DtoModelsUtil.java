package project.util;

import project.databasesModel.Office;
import project.dto.OfficeDetails;
import project.dto.OfficeRequest;

import java.math.BigDecimal;

/**
 * Created by Danylo on 27.03.2019
 */
public class DtoModelsUtil {
    private static final BigDecimal OFFICE = BigDecimal.valueOf(92);
    private static final String CITY = "LULO";
    private static final String REGION = "Zapad";
    private static final BigDecimal TARGET = BigDecimal.valueOf(567);
    private static final BigDecimal SALES = BigDecimal.valueOf(898);

    public static Office office(){
        Office office =  new Office();
        office.setOffice(OFFICE);
        office.setCity(CITY);
        office.setRegion(REGION);
        office.setTarget(TARGET);
        office.setSales(SALES);
        return office;
    }

    public static OfficeRequest officeRequest(){
        OfficeRequest officeRequest = new OfficeRequest();
        officeRequest.setOffice(OFFICE);
        officeRequest.setCity(CITY);
        officeRequest.setRegion(REGION);
        officeRequest.setOfficeDetails(officeDetails());
        return officeRequest;
    }

    public static OfficeDetails officeDetails(){
        OfficeDetails officeDetails = new OfficeDetails();
        officeDetails.setTarget(TARGET);
        officeDetails.setSales(SALES);
        return officeDetails;
    }

}
