package kz.hbscale.main.model;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.enums.ConstructionType;
import kz.hbscale.main.enums.TaskStatus;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="santec_task")
public class ConstructionEntity implements Serializable {

    @Id
    @SequenceGenerator(name="santec_seq", sequenceName = "santec_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "santec_seq")
    public Long id;
    public String name;
    public String address;
    public BigDecimal lng;
    public BigDecimal lat;

    public String contractor;

    public String customer;

    public String project;

    @Enumerated(value = EnumType.STRING)
    public ConstructionType type;

    @Column(name = "from_quarter")
    public String fromQuarter;

    @Column(name = "from_year")
    public Integer fromYear;

    @Column(name = "to_quarter")
    public String toQuarter;

    @Column(name = "to_year")
    public Integer toYear;

    @ManyToOne
    @JoinColumn(name = "director", referencedColumnName = "id")
    public PersonEntity director;

    @ManyToOne
    @JoinColumn(name = "supplier", referencedColumnName = "id")
    public PersonEntity supplier;

    @ManyToOne
    @JoinColumn(name = "other", referencedColumnName = "id")
    public PersonEntity other;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    public UserEntity owner;

    @Column(name="is_removed")
    public Boolean isRemoved;

//    @Enumerated(value = EnumType.STRING)
//    @Column(name="status")
//    public TaskStatus status = TaskStatus.waiting;
//
//    @Column(name="when_contact")
//    public LocalDate whenContact;

    @Column(columnDefinition = "timestamp default now()")
    public LocalDateTime created;
}
