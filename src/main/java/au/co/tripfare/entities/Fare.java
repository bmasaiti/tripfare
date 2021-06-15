package au.co.tripfare.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@Data
@Table(name = "fare")
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqgen")
    int id;
    String fromStop;
    String toStop;
    Double fareValue;
}
