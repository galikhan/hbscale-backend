package kz.hbscale.main.repository;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.model.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {

    List<UserTaskEntity> findByOwnerId(Long owner);

//    List<UserTaskEntity> findBy(Long owner);

    @Query(nativeQuery = true)
    List<ResultDto> getResults();

}
