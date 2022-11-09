package kz.hbscale.main.dto;

import java.util.List;
import java.util.Map;

public class UserTaskAndConstruction {

    public List<UserTaskDto> tasks;
    public List<ConstructionDto> constructions;

    public UserTaskAndConstruction(List<UserTaskDto> dtoTasks, List<ConstructionDto> constructions) {
        this.tasks = dtoTasks;
        this.constructions = constructions;
    }
}
