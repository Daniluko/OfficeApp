package project.databasesModel;

import java.math.BigDecimal;

public interface CustomerDao {

    boolean insertCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(BigDecimal id);
}
