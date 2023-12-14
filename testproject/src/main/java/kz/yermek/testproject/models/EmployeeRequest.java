package kz.yermek.testproject.models;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    private int personalNumber;
    private String secondName;
    private String firstName;
    private String thirdName;
    private LocalDate dateOfBirth;
    private int position;
    private int rank;
    private int department;
    private LocalDate dateOfSign;
    private int contractPeriod;
    private int images;
}