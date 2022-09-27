package kz.hbscale.main.dto;

public class ConstructionPeriod {

    public String quarter;
    public Integer year;

    public ConstructionPeriod() {
    }

    public ConstructionPeriod(String quarter, Integer year) {
        this.quarter = quarter;
        this.year = year;
    }
}
