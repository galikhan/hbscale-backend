package kz.hbscale.main.repository;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByOwnerId(Long owner);

    @Query(nativeQuery = true)
    List<ResultDto> getResults();

}
