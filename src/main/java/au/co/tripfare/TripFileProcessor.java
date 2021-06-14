package au.co.tripfare;

import au.co.tripfare.entities.Tap;
import au.co.tripfare.entities.Trip;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.*;
import java.util.List;

public class TripFileProcessor {

    public static List<Tap> readTapsFromFile(String fileName) throws FileNotFoundException{

            List taps = new CsvToBeanBuilder(
                    new FileReader(fileName))
                    .withType(Tap.class)
                    .build()
                    .parse();
            return taps;

    }

    public static void streamTripsToFile(List<Trip> trips, String fileName) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter(fileName);
        HeaderColumnNameMappingStrategy<Trip> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(Trip.class);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(trips);
        writer.close();
    }

}
