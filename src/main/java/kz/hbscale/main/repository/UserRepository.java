package kz.hbscale.main.repository;

import kz.hbscale.main.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);

}
