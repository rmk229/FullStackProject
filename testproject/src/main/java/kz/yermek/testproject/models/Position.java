package kz.yermek.testproject.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "positions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Position {

    @Id
    @Column(name = "id", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position_name", length = 30)
    private String positionName;

    public Position(int id) {
        this.id = id;
    }
}
