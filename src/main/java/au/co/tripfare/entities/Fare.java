package au.co.tripfare.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "fare")
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;
    String fromStop;
    String toStop;
    Double fareValue;
}
