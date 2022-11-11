package kz.hbscale.main.service;

import kz.hbscale.main.dto.ConstructionDto;
import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.dto.UserTaskAndConstruction;
import kz.hbscale.main.dto.UserTaskDto;
import kz.hbscale.main.enums.TaskStatus;
import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.model.UserTaskEntity;
import kz.hbscale.main.repository.ConstructionRepository;
import kz.hbscale.main.repository.UserRepository;
import kz.hbscale.main.repository.UserTaskRepository;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTaskService {

    private UserTaskRepository userTaskRepository;
    private Logger logger = LogManager.getLogger(UserTaskService.class);
    private AuthenticationFacade authenticationFacade;
    private UserRepository userRepository;
    private ConstructionRepository constructionRepository;

    public UserTaskService(UserTaskRepository userTaskRepository,
                           AuthenticationFacade authenticationFacade,
                           ConstructionRepository constructionRepository,
                           UserRepository userRepository) {
        this.userTaskRepository = userTaskRepository;
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
        this.constructionRepository = constructionRepository;
    }


    public UserTaskDto save(UserTaskDto dto) {

        logger.info("dto {}" + dto.toString());
        String username = (String) authenticationFacade.getAuthentication().getPrincipal();

        UserTaskEntity entity   =  new UserTaskEntity();
        entity.description = dto.description;
        entity.isRemoved  = false;
        entity.status   = TaskStatus.waiting;

        if(dto.id != null) {
            entity.id = dto.id;
            entity.status = TaskStatus.valueOf(dto.status);
            entity.created = dto.created;
        } else {
            entity.created = LocalDateTime.now();
        }

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        entity.whenContact = LocalDate.parse(dto.whenContact, pattern);

        UserEntity user = userRepository.findByUsername(username);
        entity.owner = user;
        Optional<ConstructionEntity> constructionEntity = constructionRepository.findById(dto.construction);
        if(constructionEntity.isPresent()) {
            entity.construction = constructionEntity.get();
        }
        userTaskRepository.save(entity);
        return new UserTaskDto(entity);
    }

    public UserTaskAndConstruction myTasks() {

        String username = (String) authenticationFacade.getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(username);
        List<UserTaskEntity>  tasks = userTaskRepository.findByOwnerId(user.id);

        List<ConstructionEntity> constructions = userTaskRepository.myTasksConstructions(user.id);
        List<ConstructionDto> dtoConstructions = constructions.stream().distinct().map(ConstructionDto::new).collect(Collectors.toList());
        List<UserTaskDto> dtoTasks = tasks.stream().map(UserTaskDto::new).collect(Collectors.toList());

        return new UserTaskAndConstruction(dtoTasks, dtoConstructions);
    }

    public List<ResultDto> getCountTaskInPeriod(LocalDate begin, LocalDate end) {
        return userTaskRepository.getCountTaskInPeriod(begin, end);
    }

    public List<ResultDto> getMonthlyCount() {
        logger.info("get monthly");
        LocalDate begin = LocalDate.now();
        int daysInMonth = begin.lengthOfMonth();
        begin = begin.minus(daysInMonth, ChronoUnit.DAYS);
        LocalDate end = LocalDate.now();
        return getCountTaskInPeriod(begin, end);
    }


    public List<ResultDto> getLast7DaysCount() {
        logger.info("get last 7 days");
        LocalDate begin = LocalDate.now();
        begin = begin.minus(7, ChronoUnit.DAYS);
        LocalDate end = LocalDate.now();
        return getCountTaskInPeriod(begin, end);
    }

    public UserTaskAndConstruction getAllLast7DaysTasks() {
        List<UserTaskEntity> tasks = userTaskRepository.findTaskInPeriod(sevenDaysBeforeDate(), currentDate());
        List<ConstructionEntity> constructions = constructionRepository.tasksConstructionsInDateRange(sevenDaysBeforeDate(), currentDate());
        return new UserTaskAndConstruction(UserTaskDto.toDto(tasks), ConstructionDto.toDto(constructions));
    }

    public UserTaskAndConstruction getAllMonthlyTasks() {
        List<UserTaskEntity> tasks = userTaskRepository.findTaskInPeriod(monthBeforeDate(), currentDate());
        List<ConstructionEntity> constructions = constructionRepository.tasksConstructionsInDateRange(monthBeforeDate(), currentDate());
        return new UserTaskAndConstruction(UserTaskDto.toDto(tasks), ConstructionDto.toDto(constructions));
    }

    public LocalDate sevenDaysBeforeDate() {
        LocalDate begin = LocalDate.now();
        return begin.minus(7, ChronoUnit.DAYS);
    }

    public LocalDate monthBeforeDate() {
        LocalDate begin = LocalDate.now();
        int daysInMonth = begin.lengthOfMonth();
        return begin.minus(daysInMonth, ChronoUnit.DAYS);
    }

    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
