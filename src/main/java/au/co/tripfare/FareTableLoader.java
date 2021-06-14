package au.co.tripfare;

import au.co.tripfare.repository.FareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


public class FareTableLoader implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(FareTableLoader.class);
    @Autowired
    FareRepository fareRepository;
    @Override
    public void run(String... args) throws Exception {
        // fareRepository.save()   ;
    }
}
