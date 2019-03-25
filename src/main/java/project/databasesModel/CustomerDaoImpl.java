package project.databasesModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Locale;

public class CustomerDaoImpl implements CustomerDao {
    private static EntityManagerFactory factory;

    static {
        Locale.setDefault(Locale.ENGLISH);
        factory = Persistence.createEntityManagerFactory("PERSISTENCE");
    }

    private static final Logger LOG = LogManager.getLogger(CustomerDaoImpl.class);

    private EntityManager entityManager = factory.createEntityManager();

    @Override
    public boolean insertCustomer(Customer customer)  {
        LOG.debug("persisting Customer instance");
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
            LOG.debug("persisting successful");
            return true;
        } catch (RuntimeException e) {
            if (entityManager != null) {
                System.out.println("Transaction is being rolled back.");
               entityManager.getTransaction().rollback();
            }
            LOG.error("persist failed", e);
            throw e;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer)   {
        LOG.debug("merge Customer instance");
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(customer);
            entityManager.getTransaction().commit();
            LOG.debug("merge successful");
            return true;
        } catch (RuntimeException e) {
            if (entityManager != null) {
                System.out.println("Transaction is being rolled back.");
                entityManager.getTransaction().rollback();
            }
            LOG.error("merge failed", e);
            throw e;
        }
    }

    @Override
    public boolean deleteCustomer(BigDecimal id)   {
        LOG.debug("remove Customer instance");
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Customer.class, id));
            entityManager.getTransaction().commit();
            LOG.debug("remove successful");
            return true;
        } catch (RuntimeException e) {
            if (entityManager != null) {
                System.out.println("Transaction is being rolled back.");
                entityManager.getTransaction().rollback();
            }
            LOG.error("remove failed", e);
            throw e;
        }
    }
}
