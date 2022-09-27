package kz.hbscale.main.dto;

import kz.hbscale.main.model.PersonEntity;

public class PersonDto {

    public String fullname;
    public String phone;

    public PersonDto() {
    }

    public PersonDto(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }

    public PersonDto(PersonEntity director) {
        this.fullname = director.fullname;
        this.phone = director.phone;
    }
}
