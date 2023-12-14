package kz.yermek.testproject.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "images")
public class Images {
    @Id
    @Column(name = "id", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;

    @Lob
    private byte[] images;

    public Images(int id) {
        this.id = id;
    }
}
