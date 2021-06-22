package au.co.tripfare;

import au.co.tripfare.model.Tap;
import au.co.tripfare.model.Trip;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TripFileProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(TripFileProcessor.class);

    private TripFileProcessor() {
    }

    public static List readTapsFromFile(InputStream inputFile) throws IOException {
        if (inputFile == null) {
            throw new IOException("No file uploaded!");
        }
        var streamReader = new InputStreamReader(inputFile, StandardCharsets.UTF_8);

        return new CsvToBeanBuilder(streamReader)
                .withIgnoreLeadingWhiteSpace(true)
                .withType(Tap.class)
                .build()
                .parse();

    }

    public static void streamTripsToFile(List<Trip> trips, File fileName) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter(fileName);
        HeaderColumnNameMappingStrategy<Trip> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(Trip.class);
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withMappingStrategy(strategy)
                .withSeparator(',')
                .build();
        beanToCsv.write(trips);
        writer.close();
        System.out.println(trips);
        System.out.println("Processed trips file printed");
        return;
    }

}
