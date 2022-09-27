package kz.hbscale.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "santec_person")
public class PersonEntity {

    public PersonEntity() {
    }

    @Id
    @SequenceGenerator(name="person_seq", sequenceName = "person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    private Long id;

    public String fullname;
    public String phone;

    @Column(name="is_removed")
    public Boolean isRemoved;

    public PersonEntity(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }
}
