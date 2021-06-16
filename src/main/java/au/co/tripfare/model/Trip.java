package au.co.tripfare.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.TripStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Setter
@Getter
@ToString
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
    private String companyId;
    @CsvBindByName
    private String busId;
    @CsvBindByName
    @CsvDate("DD-MM-yyyy HH:MM:SS")
    private LocalDateTime startTime;
    @CsvBindByName
    @CsvDate("DD-MM-yyyy HH:MM:SS")
    private LocalDateTime endTime;


}