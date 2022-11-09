package kz.hbscale.main.model;

import kz.hbscale.main.dto.ConstructionDto;
import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.enums.TaskStatus;
import org.hibernate.annotations.Fetch;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NamedNativeQuery(name="UserTaskEntity.getResults",
        query = "select "
                + " users.username as owner,"
                + " count(case when status='waiting' then 1 else null end) as waiting,"
                + " count(case when status='processing' then 1 else null end) as processing,"
                + " count(case when status='finished' then 1 else null end) as finished "
                + " from tasks join users on users.id = tasks.owner group by users.username",
        resultSetMapping = "Mapping.getResults")
@SqlResultSetMapping(name="Mapping.getResults",
        classes = @ConstructorResult(targetClass = ResultDto.class, columns = {
                @ColumnResult(name="owner", type=String.class),
                @ColumnResult(name="waiting", type=Integer.class),
                @ColumnResult(name="processing", type=Integer.class),
                @ColumnResult(name="finished", type=Integer.class),
        }))

@NamedNativeQuery(name="UserTaskEntity.myTasksConstruction",
        query = "select c.* "
                + " from tasks t join construction c "
                + " on c.id = t.construction "
                + " where t.owner = ?1",
        resultClass = ConstructionEntity.class)
@Entity
@Table(name = "tasks")
public class UserTaskEntity {

    @Id
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    public UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "executor", referencedColumnName = "id")
    public UserEntity executor;

    @Column(name = "is_removed", columnDefinition = "boolean default false")
    public Boolean isRemoved = false;

    public String description;

    @Enumerated(value = EnumType.STRING)
    public TaskStatus status = TaskStatus.waiting;

    @Column(name = "when_contact")
    public LocalDate whenContact;

    public LocalDateTime created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name="construction")
    public  ConstructionEntity construction;

}
