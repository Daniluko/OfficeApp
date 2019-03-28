package project.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeDetails)) return false;
        OfficeDetails that = (OfficeDetails) o;
        return getTarget().equals(that.getTarget()) &&
                getSales().equals(that.getSales());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTarget(), getSales());
    }
}
