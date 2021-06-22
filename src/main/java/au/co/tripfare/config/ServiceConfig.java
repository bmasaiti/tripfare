//package au.co.tripfare.config;
//
//import au.co.tripfare.TripManager;
//import au.co.tripfare.entities.FareDomainMarkerInterface;
//import au.co.tripfare.repository.FareRepository;
//import au.co.tripfare.repository.RepositoryMarkerInterface;
//import au.co.tripfare.service.FareService;
//import au.co.tripfare.service.FareServiceImpl;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//@Configuration
//@EnableJpaRepositories(basePackageClasses = {RepositoryMarkerInterface.class})
//@EntityScan(basePackageClasses = {FareDomainMarkerInterface.class})
//public class ServiceConfig {
//    @Bean
//    public FareService fareService(FareRepository fareRepository){
//        return new FareServiceImpl(fareRepository);
//    }
//    @Bean
//   public  TripManager tripManager(FareRepository fareRepository, FareService fareService){
//        return new TripManager(fareRepository,fareService);
//    }
//
//
//
//
//}
