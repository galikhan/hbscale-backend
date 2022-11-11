package kz.hbscale.main.repository;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.model.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {

    @Query(value="Select t from UserTaskEntity t where t.owner.id = owner  order by t.whenContact ")
    List<UserTaskEntity> findByOwnerId(Long owner);
//    List<UserTaskEntity> findBy(Long owner);

    @Query(nativeQuery = true)
    List<ResultDto> getCountTaskInPeriod(@Param("begin") LocalDate begin, @Param("end") LocalDate end);

    @Query(value="Select c From ConstructionEntity c join UserTaskEntity t on c.id = t.construction where t.owner.id = :owner")
    List<ConstructionEntity> myTasksConstructions(@Param("owner") Long owner);

//    @Query(nativeQuery = true,
//            value=" select c.* from construction c join tasks t"
//                    + " on c.id = t.construction "
//                    + " where date(t.created) between date(:begin) and date(:end)")
//
//    @Query(value="select c.* from construction c"
//            + " join tasks t "
//            + " on c.id = t.construction "
//            + " where date(t.created) between (:begin) and (:end)",
//            nativeQuery = true)
//    List<ConstructionEntity> findTaskConstructionsInPeriod(@Param("begin") LocalDate begin, @Param("end") LocalDate end);
    @Query(nativeQuery = true,
            value="select * from tasks"
            + " where date(created) between date(:begin) and date(:end)")
    List<UserTaskEntity> findTaskInPeriod(@Param("begin") LocalDate begin, @Param("end") LocalDate end);

}
