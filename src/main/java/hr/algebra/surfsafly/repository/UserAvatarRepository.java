package hr.algebra.surfsafly.repository;

import hr.algebra.surfsafly.model.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {
    Optional<UserAvatar> findByUserIdAndIsProfilePictureTrue(Long userId);
    List<UserAvatar> findAllByUserId(Long userId);
    UserAvatar findByAvatarId (Long avatarId);
    UserAvatar findByAvatarIdAndUserId (Long avatarId, Long userId);
}
