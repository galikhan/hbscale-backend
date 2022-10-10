package kz.hbscale.main.dto;


import kz.hbscale.main.model.TaskEntity;

import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

public class TaskDto {
    public String status;
    public Long id;
    public String name;
    public String address;
    public PersonDto director;
    public PersonDto supplier;
    public PersonDto other;
    public ConstructionPeriod from;
    public ConstructionPeriod to;
    public String contractor;
    public String project;
    public String customer;
//    public Long contractor;
//    public Long project;
//    public Long customer;
    public BigDecimal lat;
    public BigDecimal lng;
    public String whenContact;
    public Integer daysLeft = -1;

    public String commonUUID;
    public TaskDto() {
    }

    public TaskDto(TaskEntity taskEntity) {
        this.id = taskEntity.id;
        this.name = taskEntity.name;
        this.from = new ConstructionPeriod(taskEntity.fromQuarter, taskEntity.fromYear);
        this.to = new ConstructionPeriod(taskEntity.toQuarter, taskEntity.toYear);
        this.lat = taskEntity.lat;
        this.lng = taskEntity.lng;
        this.address = taskEntity.address;

        this.director = new PersonDto(taskEntity.director);
        if (taskEntity.other != null) {
            this.other = new PersonDto(taskEntity.other);
        }
        if (taskEntity.supplier != null) {
            this.supplier = new PersonDto(taskEntity.supplier);
        }

//        if (taskEntity.contractor != null) {
//            this.contractor = taskEntity.contractor.id;
//        }
//        if (taskEntity.project != null) {
//            this.project = taskEntity.project.id;
//        }
//        if (taskEntity.customer != null) {
//            this.customer = taskEntity.customer.id;
//        }
        this.contractor = taskEntity.contractor;
        this.project = taskEntity.project;
        this.customer = taskEntity.customer;

        this.status = taskEntity.status.name();
        if(taskEntity.whenContact != null) {
            this.whenContact = taskEntity.whenContact.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.daysLeft = taskEntity.whenContact.getDayOfYear() - LocalDate.now().getDayOfYear();
        }

    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", director=" + director +
                ", supplier=" + supplier +
                ", other=" + other +
                ", from=" + from +
                ", to=" + to +
                ", contractor=" + contractor +
                ", project=" + project +
                ", customer=" + customer +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
