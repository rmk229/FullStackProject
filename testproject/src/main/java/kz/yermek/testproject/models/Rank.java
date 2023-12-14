package kz.yermek.testproject.models;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ranks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rank {

    @Id
    @Column(name = "id", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rank_name", length = 30)
    private String rankName;

    public Rank(int id) {
        this.id = id;
    }
}