package kz.hbscale.main.repository;

import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.model.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DictionaryRepository extends JpaRepository<DictionaryEntity, Long> {

    public List<DictionaryEntity> findByKey(String key);


}
