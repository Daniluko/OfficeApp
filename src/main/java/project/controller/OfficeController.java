package project.controller;

import project.databasesModel.Office;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.springData.exception.UpdateException;
import project.springData.service.OfficesService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Danylo on 19.03.2019
 */

@RestController
@RequestMapping("/offices")
public class OfficeController {
    private static final Logger LOG = LogManager.getLogger(OfficeController.class);

    @Autowired
    private OfficesService officesService;

    @GetMapping("/{id}")
    public @ResponseBody
    Office getById(@PathVariable("id") int id){
        LOG.info("get by id={}",id);
        Office result = officesService.findOfficeById(BigDecimal.valueOf(id));
        return result;
    }

    @GetMapping
    public @ResponseBody
    Set<Office> getOfficeFirstByTargetAfter(@RequestParam(value = "target",required = false) BigDecimal target){
        LOG.info("get first office where target more than " + target);
        if (Objects.isNull(target)){
            LOG.debug("This method use method GetAllOffices");
            return officesService.getAllOffice();
        }
        Set<Office> offices = officesService.findOfficeFirstByTargetAfter(target);
        LOG.info("Office found : ");
        return offices;
    }

    @PutMapping("/{id}")
    public @ResponseBody
    void updateOffice(@PathVariable("id") int id, @RequestParam("region") String region ){
        LOG.info("Update office ");
        Office office = officesService.findOfficeById(BigDecimal.valueOf(id));
        if (Objects.isNull(office)){
            LOG.warn("Update cannot update , office with this id not found ");
            throw new UpdateException("Sorry , but office with this id not found ");
        }else {
            office.setRegion((region));
            officesService.updateOffice(office);
        }
        LOG.info("successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable("id") int id){
        LOG.info("Delete office by id " + id);
        officesService.deleteOffice(BigDecimal.valueOf(id));
        LOG.info("successfully");
    }


}