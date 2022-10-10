package kz.hbscale.main.fileupload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table
@Entity(name="files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_seq")
    @SequenceGenerator(name="files_seq", sequenceName = "files_seq", allocationSize = 1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String filename;

    public String path;

    public LocalDateTime created;

    public Long container;

    @Column(name = "common_uuid")
    public String commonUUID;

    @Column(name = "container_type")
    public ContainerTypEnum containerType;

    public FileEntity() {
    }
}
