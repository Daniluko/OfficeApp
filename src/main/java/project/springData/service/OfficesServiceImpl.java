package project.springData.service;

import project.databasesModel.Office;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import project.springData.exception.DeleteException;
import project.springData.repository.OfficesRepository;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Danylo on 16.03.2019
 */

@Service
@Transactional
public class OfficesServiceImpl implements OfficesService {
    private static final Logger LOG = LogManager.getLogger(OfficesServiceImpl.class);

    @Autowired
    private OfficesRepository officesRepository;

    @Override
    public Set<Office> getAllOffice() {
    LOG.debug("Get All Offices");
        HashSet<Office> offices = new HashSet<>(officesRepository.findAll());
    LOG.debug("Get All Offices return {} records", offices.size());
    return offices;
    }

    @Override
    public Set<Office> findOfficeFirstByTargetAfter(BigDecimal target) {
        LOG.debug("Find first office after target " + target);
        HashSet<Office> offices  = new HashSet<>(officesRepository.findFirstByTargetAfter(target));
        LOG.debug("successfully " );
        return offices;
    }


    @Override
    public Office findOfficeById(BigDecimal id) {
        LOG.debug("Find Office by Id");
        Office office = officesRepository.findById(id).orElse(null);
        LOG.debug("successfully");
        return office;
    }

    @Override
    public void insertOffice(Office office) {
        LOG.debug("Insert Office, office = {} ", office);
        officesRepository.save(office);
        LOG.debug("insert successfully");
    }

    @Override
    public void updateOffice(Office office) {
        LOG.debug("Update Office, office = {} ", office);
        officesRepository.save(office);
        LOG.debug("update successfully");
    }

    @Override
    public void deleteOffice(BigDecimal id) {
        LOG.debug("Delete Office, id = {} ", id);
        try {
            officesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            LOG.warn("Cannot delete office with id{}, because don't present", id);
            throw new DeleteException("Cannot delete Office by Id = " + id + ", because it don't present");
        }
        LOG.debug("delete successfully");
    }
}
