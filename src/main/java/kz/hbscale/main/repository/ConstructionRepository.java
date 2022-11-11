package kz.hbscale.main.repository;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.model.ConstructionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConstructionRepository extends JpaRepository<ConstructionEntity, Long> {

    List<ConstructionEntity> findByOwnerId(Long owner);

    @Query(value="select c.* from construction c"
            + " join tasks t "
            + " on c.id = t.construction "
            + " where date(t.created) between (:begin) and (:end)",
            nativeQuery = true)
    List<ConstructionEntity> tasksConstructionsInDateRange(@Param("begin") LocalDate begin, @Param("end") LocalDate end);


}
