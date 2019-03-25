package project.databasesModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class OrderDaoImpl implements OrderDao {

    private static EntityManagerFactory factory;

    static {
        Locale.setDefault(Locale.ENGLISH);
        factory = Persistence.createEntityManagerFactory("PERSISTENCE");
    }

    private static final Logger LOG = LogManager.getLogger(OrderDaoImpl.class);

    private EntityManager entityManager = factory.createEntityManager();


    @Override
    public Set<Order> getAllOrders() {
        LOG.debug("getting all Orders instances");
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> rootEntry = cq.from(Order.class);
            CriteriaQuery<Order> all = cq.select(rootEntry);
            TypedQuery<Order> allQuery = entityManager.createQuery(all);
            allQuery.setFirstResult(1);
            allQuery.setMaxResults(2);
            return new HashSet<>(allQuery.getResultList());
        } catch (RuntimeException re) {
            LOG.error("getAll failed", re);
            throw re;
        }

    }

    @Override
    public Order findOrderById(BigDecimal id)   {
        LOG.debug("getting Order instance with id: " + id);
        try {
            Order instance = entityManager.find(Order.class, id);
            LOG.debug("get successful");
            return instance;
        } catch (RuntimeException e) {
            LOG.error("get failed", e);
            throw e;
        }
    }

    @Override
    public boolean insertOrder(Order order)   {
        LOG.debug("persisting Order instance");
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();
            LOG.debug("persist successful");
            return true;
        } catch (RuntimeException re) {
            if (entityManager != null) {
                System.out.println("Transaction is being rolled back.");
                entityManager.getTransaction().rollback();
            }
            LOG.error("persist failed", re);
            throw re;
        }
    }

    @Override
    public boolean updateOrder(Order order)   {
        LOG.debug("merge Order instance");
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(order);
            entityManager.getTransaction().commit();
            LOG.debug("merge successful");
            return true;
        } catch (RuntimeException re) {
            if (entityManager != null) {
                System.out.println("Transaction is being rolled back.");
                entityManager.getTransaction().rollback();
            }
            LOG.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public boolean deleteOrder(BigDecimal id)   {
        LOG.debug("remove Order instance");
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Order.class, id));
            entityManager.getTransaction().commit();
            LOG.debug("remove successful");
            return true;
        } catch (RuntimeException re) {
            if (entityManager != null) {
                System.out.println("Transaction is being rolled back.");
                entityManager.getTransaction().rollback();
            }
            LOG.error("remove failed", re);
            throw re;
        }
    }
}