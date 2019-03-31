package project.controller;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import project.springData.config.MainMvcConfig;
import project.util.DtoModelsUtil;

import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Danylo on 28.03.2019
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MainMvcConfig.class })
@WebAppConfiguration
@TestPropertySource("classpath:/test.properties")
public class OfficeControllerSecurityTest {

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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testInsertOfficeAuth() throws Exception {
        String json = mapper.writeValueAsString(DtoModelsUtil.officeRequest());
        MvcResult mvcResult = mockMvc.perform(post("/offices").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testInsertOfficeNotAuth() throws Exception {
        String json = mapper.writeValueAsString(DtoModelsUtil.officeRequest());
        MvcResult mvcResult = mockMvc.perform(post("/offices").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andReturn();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteOfficeExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/offices/{id}", "99")).andDo(print()).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateOfficeExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/offices/{id}", "99").param("region", "Jond")).andDo(print())
                .andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddOfficeNoAuth() throws Exception {
        String json = mapper.writeValueAsString(DtoModelsUtil.officeRequest());
        MvcResult mvcResult = mockMvc.perform(post("/offices").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andReturn();

        checkUnauthorizedStatus(mvcResult);
    }

    private void checkUnauthorizedStatus(MvcResult mvcResult) {
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteOfficeExistNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/offices/{id}", "99")).andDo(print()).andReturn();
        checkUnauthorizedStatus(mvcResult);
    }

    @Test
    public void testDeleteOfficeNotExistNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/offices/{id}", "-1")).andDo(print()).andReturn();
        checkUnauthorizedStatus(mvcResult);
    }

    @Test
    public void testUpdateOfficeExistNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/offices/{id}", "98").param("region", "Some")).andDo(print())
                .andReturn();
        checkUnauthorizedStatus(mvcResult);
    }

    @Test
    public void testUpdateOrderNotExistNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/offices/{id}", "-1").param("region", "Some")).andDo(print()).andReturn();
        checkUnauthorizedStatus(mvcResult);
    }


}

