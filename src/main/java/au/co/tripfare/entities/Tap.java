package au.co.tripfare.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByNames;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.TapType;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
public class Tap {
    @CsvBindByName
    private int id;
    @CsvBindByName(column="DateTimeUTC" )
    @CsvDate ("DD-MM-YYYY HH:MM:SS")
    private Date tapTime;
    @CsvBindByName
    private String tapType;
    @CsvBindByName
    private String stopId;
    @CsvBindByName
    private String companyId;
    @CsvBindByName
    private String busId;
    @CsvBindByName
    private String pan;
}