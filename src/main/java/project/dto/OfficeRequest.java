package project.dto;


import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by Danylo on 26.03.2019
 */
public class OfficeRequest {
    @NotNull(message = "1")
    @Min(value = 10, message = "1")
    @Max(value = 100,message = "1")
    private BigDecimal office;
    @NotNull(message = "2")
    private String city;
    @NotNull(message = "3")
    private String region;
    @Valid
    @NotNull(message = "6")
    private OfficeDetails officeDetails;

    public BigDecimal getOffice() {
        return office;
    }

    public void setOffice(BigDecimal office) {
        this.office = office;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public OfficeDetails getOfficeDetails() {
        return officeDetails;
    }

    public void setOfficeDetails(OfficeDetails officeDetails) {
        this.officeDetails = officeDetails;
    }

    @Override
    public String toString() {
        return "OfficeRequest{" +
                "office=" + office +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", officeDetails=" + officeDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeRequest)) return false;
        OfficeRequest that = (OfficeRequest) o;
        return getOffice().equals(that.getOffice()) &&
                getCity().equals(that.getCity()) &&
                getRegion().equals(that.getRegion()) &&
                getOfficeDetails().equals(that.getOfficeDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOffice(), getCity(), getRegion(), getOfficeDetails());
    }
}
