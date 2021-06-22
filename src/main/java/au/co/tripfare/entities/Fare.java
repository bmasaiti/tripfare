package au.co.tripfare.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "fare")
@AllArgsConstructor
@NoArgsConstructor
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fromstop")
    private String fromStop;
    @Column(name = "tostop")
    private String toStop;
    @Column(name = "farevalue")
    private Double fareValue;

}
