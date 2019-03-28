package project.dto;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import project.util.DtoModelsUtil;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Danylo on 28.03.2019
 */
public class OfficeRequestTest {

    private Validator validator;

    @Before
    public void setUp() {
        LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
        validator = localValidatorFactory;
    }

    @Test
    public void testOfficeRequestWrongOffice(){
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setOffice(BigDecimal.valueOf(123123));
        Errors errors = new BeanPropertyBindingResult(officeRequest,"officeRequest");
        validator.validate(officeRequest,errors);
        assertEquals(errors.getFieldError("office").getDefaultMessage(), "1");
    }

    @Test
    public void testOfficeRequestOfficeIsNull(){
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setOffice(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("office").getDefaultMessage(), "1");
    }

    @Test
    public void testOfficeRequestCityIsNull(){
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setCity(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest,"officeRequest");
        validator.validate(officeRequest,errors);
        assertEquals(errors.getFieldError("city").getDefaultMessage(),"2");
    }

    @Test
    public void testOfficeRequestRegionIsNull(){
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setRegion(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest,"officeRequest");
        validator.validate(officeRequest,errors);
        assertEquals(errors.getFieldError("region").getDefaultMessage(),"3");
    }

    @Test
    public void testOfficeRequestValid(){
        OfficeRequest officeRequest= DtoModelsUtil.officeRequest();
        Errors errors = new BeanPropertyBindingResult(officeRequest,"officeRequest");
        validator.validate(officeRequest,errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testOfficeRequestOfficeDetailsIsNull(){
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setOfficeDetails(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest,"officeRequest");
        validator.validate(officeRequest,errors);
        assertEquals(errors.getFieldError("officeDetails").getDefaultMessage(),"6");
    }
}