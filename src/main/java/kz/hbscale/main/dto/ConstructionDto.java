package kz.hbscale.main.dto;


import kz.hbscale.main.model.ConstructionEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConstructionDto {
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
    public String commonUUID;

    public String type;
    public ConstructionDto() {
    }

    public ConstructionDto(ConstructionEntity taskEntity) {
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
        if(taskEntity.type !=null ) {
            this.type = taskEntity.type.name();
        }
//        this.status = taskEntity.status.name();
//        if(taskEntity.whenContact != null) {
//            this.whenContact = taskEntity.whenContact.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            this.daysLeft = taskEntity.whenContact.getDayOfYear() - LocalDate.now().getDayOfYear();
//        }

    }

    public static List<ConstructionDto> toDto(List<ConstructionEntity> constructions) {
        return constructions.stream().map(ConstructionDto::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ConstructionDto{" +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructionDto that = (ConstructionDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
