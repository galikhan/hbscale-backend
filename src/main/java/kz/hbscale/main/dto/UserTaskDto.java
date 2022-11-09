package kz.hbscale.main.dto;

import kz.hbscale.main.model.UserTaskEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserTaskDto {

    public  Long id;
    public  String description;
    public String whenContact;
    public LocalDateTime created;
    public Long construction;
    public   String   owner;
    public String status;


    public UserTaskDto() {
    }

    public UserTaskDto(UserTaskEntity entity) {

        this.id = entity.id;
        this.description =  entity.description;
        if(entity.construction != null) {
            this.construction = entity.construction.id;
        }
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(entity.whenContact != null) {
            this.whenContact =  entity.whenContact.format(pattern);
        }
        if(entity.status != null) {
            this.status = entity.status.name();
        }
        this.owner = entity.owner.username;
        this.created = entity.created;
    }
}
