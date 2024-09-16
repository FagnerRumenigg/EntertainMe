package entertain_me.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="total_rating_users")
public class TotalRatingUsers {

    @Id
    @Column(name = "id_anime")
    private Long id;

    @Column(name = "total_rating")
    private Double totalRating;

    @Column(name ="total_votes")
    private Integer totalVotes;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

}
