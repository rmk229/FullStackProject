package kz.yermek.testproject.models;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 5)
    private int id;

    @Column(name = "personal_number", length = 10)
    private int personalNumber;

    @Column(name = "second_name", length = 30)
    private String secondName;

    @Column(name = "first_name", length = 30)
    private String firstName;

    @Column(name = "third_name", length = 30)
    private String thirdName;

    @Column(name = "picture")
    private String picture;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_sign")
    private LocalDate dateOfSign;

    @Column(name = "contract_period", length = 2)
    private int contractPeriod;

    @ManyToOne(fetch = FetchType.EAGER)
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER)
    private Rank rank;

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;
}
