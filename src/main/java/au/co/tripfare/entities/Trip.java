package au.co.tripfare.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import utils.TripStatus;
import java.util.Date;

@Builder
@Setter
@Getter
public class Trip {
    @CsvBindByName
    private String pan;
    @CsvBindByName
    private String FromStop;
    @CsvBindByName
    private String toStop;
    @CsvBindByName
    private Double ChargeAmount;
    @CsvBindByName
    private TripStatus tripStatus;
    @CsvBindByName
    private String companyId   ;
    @CsvBindByName
    private String busId;
    @CsvBindByName
    @CsvDate("DD-MM-YYYY HH:MM:SS")
    private Date startTime;
    @CsvBindByName
    @CsvDate("DD-MM-YYYY HH:MM:SS")
    private Date endTime;

    @Override
    public String toString() {

        return startTime + "," + endTime + "," + (endTime.getTime() - startTime.getTime()) + "," + FromStop
                + "," + toStop + "," + ChargeAmount + "," + companyId + "," + busId + "," + pan + "," + tripStatus;
    }
}