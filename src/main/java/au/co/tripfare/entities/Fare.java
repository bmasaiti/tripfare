package au.co.tripfare.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ToString
@Getter
@Setter
public class Fare {
    @Id
    int id;
    String fromStop;
    String toStop;
    Double fareValue;
}
