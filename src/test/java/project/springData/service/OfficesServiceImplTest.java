package project.springData.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import project.databasesModel.Office;
import project.springData.repository.OfficesRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Danylo on 24.03.2019
 */
@RunWith(MockitoJUnitRunner.class)
public class OfficesServiceImplTest {
    @Mock
    private OfficesRepository officesRepository;

    @Spy
    @InjectMocks
    private OfficesService officesService = new OfficesServiceImpl();

    private Office office1 = new Office();
    private Office office2 = new Office();

    @Test
    public void testGetAllOffice() {
        List<Office> offices = Arrays.asList(office1, office2);
        doReturn(offices).when(officesRepository).findAll();
        Set<Office> office = officesService.getAllOffice();
        assertTrue(offices.containsAll(office) && office.containsAll(offices));
    }

    @Test
    public void testFindOfficeFirstByTargetAfter() {
        List<Office> offices =  Arrays.asList(office1, office2);
        doReturn(offices).when(officesRepository).findFirstByTargetAfter(BigDecimal.valueOf(300));
        Set<Office> office = officesService.findOfficeFirstByTargetAfter(BigDecimal.valueOf(300));
        assertTrue(offices.containsAll(office)&& office.containsAll(offices));
    }

    @Test
    public void testFindOfficeById() {
        doReturn(Optional.of(office1)).when(officesRepository).findById(any());
        Office result = officesService.findOfficeById(BigDecimal.ONE);
        assertEquals(office1, result);
        System.out.println(result);
    }

    @Test
    public void testInsertOffice() {
        doReturn(office1).when(officesRepository).save(any());
        officesService.insertOffice(office1);
        verify(officesRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateOffice() {
        doReturn(office1).when(officesRepository).save(any());
        officesService.updateOffice(office1);
        verify(officesRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteOffice() {
        doNothing().when(officesRepository).deleteById(any());
        officesService.deleteOffice(office1.getOffice());
        verify(officesRepository, times(1)).deleteById(any());
    }
}