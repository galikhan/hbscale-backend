package kz.hbscale.main.dto;

import kz.hbscale.main.model.UserEntity;

public class UserDto {

    public Long id;
    public String username;
    public String password;
    public String telegram;

    public UserDto() {
    }

    public UserDto(UserEntity entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.telegram = entity.getTelegram();
    }
}
