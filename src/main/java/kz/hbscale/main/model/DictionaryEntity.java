package kz.hbscale.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class DictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dic_seq")
    @SequenceGenerator(sequenceName = "dic_seq", name="dic_seq")
    public Long id;

    public String key;

    public String code;

    public String name;

}
