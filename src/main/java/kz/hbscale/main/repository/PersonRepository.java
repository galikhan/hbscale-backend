package kz.hbscale.main.repository;

import kz.hbscale.main.model.DictionaryEntity;
import kz.hbscale.main.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {


}
