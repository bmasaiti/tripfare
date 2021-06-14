package au.co.tripfare;

import au.co.tripfare.entities.Tap;
import au.co.tripfare.entities.Trip;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "au.co.tripfare.repository")
@EntityScan("au.co.tripfare.entities")
public class TripfareApplication {

	public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
		String inputfile = "src/main/resources/input/taps.csv" ;
		String outputfile = "src/main/resources/output/taps.csv" ;
		TripManager tripManager =  new TripManager();
		List<Tap> taps = TripFileProcessor.readTapsFromFile(inputfile);
		System.out.println("size of the taps list:"+taps.size());
		 List<Trip> trips = tripManager.processTripData(taps);
		 System.out.println("size of the trips list:"+trips.size());
		 TripFileProcessor.streamTripsToFile(trips,outputfile );
	

	}

}
