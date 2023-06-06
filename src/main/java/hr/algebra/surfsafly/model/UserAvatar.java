package hr.algebra.surfsafly.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_avatar")
public class UserAvatar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_avatar_seq")
    @SequenceGenerator(name = "user_avatar_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "avatar_id")
    Long avatarId;

    @Column(name = "is_profile_picture")
    Boolean isProfilePicture;

}
