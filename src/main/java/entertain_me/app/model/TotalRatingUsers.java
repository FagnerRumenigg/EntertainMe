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
    Long id;

    @Column(name = "total_rating")
    Double totalRating;

    @Column(name ="total_votes")
    Integer totalVotes;

    @Column(name = "last_updated")
    LocalDateTime lastUpdated;

}
