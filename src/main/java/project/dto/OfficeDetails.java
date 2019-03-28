package project.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Danylo on 26.03.2019
 */
public class OfficeDetails {
    @NotNull(message = "4")
    @Min(value = 1 , message = "4")
    @Max(value = 1000 , message = "4")
    private BigDecimal target;
    @NotNull(message = "5")
    private BigDecimal sales;

    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "OfficeDetails{" +
                "target=" + target +
                ", sales=" + sales +
                '}';
    }
}
