package kz.yermek.testproject.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Department {

    @Id
    @Column(name = "id", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "department_name", length = 30)
    private String departmentName;


}
