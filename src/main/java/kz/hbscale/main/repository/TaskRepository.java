package kz.hbscale.main.repository;

import kz.hbscale.main.model.TaskEntity;
import kz.hbscale.main.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByOwner(UserEntity owner);
}
