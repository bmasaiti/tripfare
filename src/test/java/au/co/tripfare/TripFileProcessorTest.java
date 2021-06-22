package au.co.tripfare;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import au.co.tripfare.utils.TripStatus;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TripFileProcessorTest {


    String inputPath = "/Users/oathkeeper/DEV/tripfare/src/test/resources/input_taps.csv";
    String outputPath = "/Users/oathkeeper/DEV/tripfare/src/test/resources/outputfile.csv";

    @Test
    public void shouldProduceListOfTapObjectsFromCsvFile() throws IOException {
        List<Tap> tapList = TripFileProcessor.readTapsFromFile(new FileInputStream(inputPath));
        Tap tap = tapList.get(0);
        assertEquals("yEcwlOv", tap.getBusId().trim());
        assertEquals("SvLTieMP", tap.getCompanyId().trim());
        assertEquals("sePeVy", tap.getPan().trim());
        assertEquals("ON", tap.getTapType().trim());
        assertEquals("PunMkTqDtk", tap.getStopId().trim());

    }

    @Test
    public void shouldWriteACSVFileFromListOfTripObjects() throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        String stop1 = RandomStringUtils.randomAlphabetic(10);
        String stop2 = RandomStringUtils.randomAlphabetic(9);
        String companyId = RandomStringUtils.randomAlphabetic(8);
        String busId = RandomStringUtils.randomAlphabetic(7);
        String pan = RandomStringUtils.randomAlphabetic(6);
        var outputfile = new File(outputPath);
        List<Trip> trips = new ArrayList<>();

        var trip1 = Trip.builder()
                .busId(busId)
                .companyId(companyId)
                .pan(pan)
                .tripStatus(TripStatus.COMPLETED)
                .ChargeAmount(5.63)
                .FromStop(stop1)
                .toStop(stop2)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();
        trips.add(trip1);
        TripFileProcessor.streamTripsToFile(trips, outputfile);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(outputPath), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        var record = csvRecords.iterator().next();
        assertEquals(busId, record.get("BUSID"));
        assertEquals("5.63", record.get("CHARGEAMOUNT"));
        assertEquals(companyId, record.get("COMPANYID"));
        assertEquals(pan, record.get("PAN"));
        assertEquals("COMPLETED", record.get("TRIPSTATUS"));
        assertEquals(stop1, record.get("FROMSTOP"));
        assertEquals(stop2, record.get("TOSTOP"));


    }
}
