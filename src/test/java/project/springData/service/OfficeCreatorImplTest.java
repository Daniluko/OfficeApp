package project.springData.service;

import org.junit.Test;
import project.databasesModel.Office;
import project.dto.OfficeRequest;
import project.util.DtoModelsUtil;

import static org.junit.Assert.*;

/**
 * Created by Danylo on 26.03.2019
 */
public class OfficeCreatorImplTest {

    private OfficeCreator officeCreator = new OfficeCreatorImpl();

    @Test
    public void testCreateOffice() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        Office actual = officeCreator.createOffice(officeRequest);
        Office expected = DtoModelsUtil.office();
        assertNotEquals(expected, actual);
    }
}