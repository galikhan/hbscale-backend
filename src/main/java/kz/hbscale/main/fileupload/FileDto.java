package kz.hbscale.main.fileupload;

import java.time.LocalDateTime;

public class FileDto {

    public Long id;
    public String path;
    public String filename;
    public String commonUUID;
    public LocalDateTime created;

    public FileDto() {
    }

    public FileDto(FileEntity fileEntity) {

        this.id = fileEntity.getId();
        this.path = fileEntity.path;
        this.filename = fileEntity.filename;
        this.commonUUID = fileEntity.commonUUID;
        this.created = fileEntity.created;

    }
}
