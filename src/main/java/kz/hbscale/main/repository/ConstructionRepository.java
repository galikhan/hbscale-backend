package kz.hbscale.main.repository;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.model.ConstructionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionRepository extends JpaRepository<ConstructionEntity, Long> {

    List<ConstructionEntity> findByOwnerId(Long owner);


}
