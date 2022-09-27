package kz.hbscale.main.repository;

import kz.hbscale.main.model.DictionaryEntity;
import kz.hbscale.main.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<DictionaryEntity, Long> {

    public List<DictionaryEntity> findByKey(String key);
}
