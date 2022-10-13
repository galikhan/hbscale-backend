package kz.hbscale.main.dto;

public class ResultDto {

    public String owner;
    public Number waiting;
    public Number processing;
    public Number finished;

    public ResultDto() {
    }

    public ResultDto(String owner, Number waiting, Number processing, Number finished) {
        this.owner = owner;
        this.waiting = waiting;
        this.processing = processing;
        this.finished = finished;
    }

    public Number getWaiting() {
        return waiting;
    }

    public Number getProcessing() {
        return processing;
    }

    public Number getFinished() {
        return finished;
    }

    public String getOwner() {
        return owner;
    }
}

