#Trip Fare Calculator

##  How to run
   Setup java 11 and gradle 6.8.x or 7.x 
   Run ./gradlew clean build
   
   Once build is successful , run the jar as below with two commandline args for input file and outputfile(to be created)
   
   java -jar build/libs/tripfare-0.0.1-SNAPSHOT.jar {absolute path to input csv file} {aboslute path to output file}
   
   ## Assumptions
   1. If a person taps on and off at the same stop , within 10 mins , its a cancelation
   2. If a person taps on and off at same stop with more than 10mins in between , its a round trip
   3. There are no successive tap ons for the same pan 
   4. There is no tap off without a corresponding tap on for the same pan 
   5.
   
   ## Potential improvements
   1. package the application in docker 
   2. add functionality to add fares for stop pairs to the database 
   3. Have a corresponding route table for the routes
   4. Encypting the pan
   5. Increase test coverage to more 90%
    