package hr.algebra.surfsafly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jwt_blacklist")
public class JwtBlacklistData {
    @Id
    @SequenceGenerator(name = "jwt_blacklist_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "jwt_blacklist_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "token")
    private String token;

}
