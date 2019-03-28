package project.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import project.databasesModel.Office;
import project.dto.ErrorMessage;
import project.springData.config.MainMvcConfig;
import project.util.DtoModelsUtil;

import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Danylo on 21.03.2019
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MainMvcConfig.class })
@WebAppConfiguration
@TestPropertySource("classpath:/test.properties")
public class OfficeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        Resource initSchema = new ClassPathResource("script\\schema.sql");
        Resource data = new ClassPathResource("script\\data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private List<Office> ListOfficeResult(MvcResult mvcResult)
            throws IOException, JsonParseException, JsonMappingException, UnsupportedEncodingException {
        return mapper.readValue(mvcResult.getResponse().getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, Office.class));
    }

    @Test
    public void testGetOfficeByIdExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/offices/{id}", "99")).andDo(print()).andReturn();
        Office office = mapper.readValue(mvcResult.getResponse().getContentAsString(), Office.class);
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertNotNull(office);
    }

    @Test
    public void testGetOfficeByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/offices/{id}", "99999")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().length() == 0);
    }

    @Test
    public void testGetOfficeFirstByTargetAfter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/offices").param("target", "12")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        List<Office> offices = ListOfficeResult(mvcResult);
        assertEquals(1, offices.size());
    }

    @Test
    public void testUpdateOfficeExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/offices/{id}", "99").param("region", "east")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateOfficeNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/offices/{id}", "9999").param("region", "something")).andDo(print()).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        ErrorMessage errorMessage = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorMessage.class);
        assertEquals("Sorry , but office with this id not found ", errorMessage.getMessage());
    }

    @Test
    public void testDeleteOfficeByIdExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/offices/{id}", "98")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteOfficeByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/offices/{id}", "9999")).andDo(print()).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        ErrorMessage errorMessage = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorMessage.class);
        assertEquals("Cannot delete Office by Id = 9999, because it don't present", errorMessage.getMessage());
    }

    @Test
    public void testInsertOffice() throws Exception {
        String json = mapper.writeValueAsString(DtoModelsUtil.officeRequest());
        MvcResult mvcResult = mockMvc.perform(post("/offices").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }
}