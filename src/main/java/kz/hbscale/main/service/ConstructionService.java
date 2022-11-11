package kz.hbscale.main.service;

import kz.hbscale.main.dto.ConstructionDto;
import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.enums.ConstructionType;
import kz.hbscale.main.fileupload.FileService;
import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.model.PersonEntity;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.repository.ConstructionRepository;
import kz.hbscale.main.repository.DictionaryRepository;
import kz.hbscale.main.repository.PersonRepository;
import kz.hbscale.main.repository.UserRepository;
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
public class ConstructionService {

    private AuthenticationFacade authenticationFacade;
    private ConstructionRepository constructionRepository;
    private DictionaryRepository dictionaryRepository;
    private UserRepository userRepository;
    private Logger log = LogManager.getLogger(ConstructionService.class);
    private PersonRepository personRepository;
    private FileService fileService;
    private Logger logger = LogManager.getLogger(UserTaskService.class);

    public ConstructionService(AuthenticationFacade authenticationFacade,
                               ConstructionRepository taskRepository,
                               DictionaryRepository dictionaryRepository,
                               UserRepository userRepository,
                               PersonRepository personRepository,
                               FileService fileService) {
        this.authenticationFacade = authenticationFacade;
        this.constructionRepository = taskRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.fileService = fileService;
    }

    public ConstructionDto save(ConstructionDto taskDto) {

        String username = (String) authenticationFacade.getAuthentication().getPrincipal();
        log.info("username -{}-", username);
        ConstructionEntity taskEntity = new ConstructionEntity();
        if(taskDto.id != null) {
            Optional<ConstructionEntity> task = constructionRepository.findById(taskDto.id);
            if(task.isPresent()) {
                taskEntity = task.get();
            }
        } else {
            UserEntity user = userRepository.findByUsername(username);
            taskEntity.owner = user;
            taskEntity.created = LocalDateTime.now();
        }
        taskEntity.address = taskDto.address;
        taskEntity.name = taskDto.name;
        taskEntity.fromQuarter = taskDto.from.quarter;
        taskEntity.toQuarter = taskDto.to.quarter;
        taskEntity.fromYear = taskDto.from.year;
        taskEntity.toYear = taskDto.to.year;
        taskEntity.lat = taskDto.lat;
        taskEntity.lng = taskDto.lng;

        taskEntity.contractor = taskDto.contractor;
        taskEntity.project = taskDto.project;
        taskEntity.customer = taskDto.customer;
        taskEntity.type = ConstructionType.valueOf(taskDto.type);

        PersonEntity supplier = new PersonEntity(taskDto.supplier.fullname, taskDto.supplier.phone);
        PersonEntity director = new PersonEntity(taskDto.director.fullname, taskDto.director.phone);
        PersonEntity other = new PersonEntity(taskDto.other.fullname, taskDto.other.phone);

        taskEntity.supplier = personRepository.save(supplier);
        taskEntity.director = personRepository.save(director);
        taskEntity.other = personRepository.save(other);
        this.constructionRepository.save(taskEntity);
        this.fileService.updateContainerByCommonUUID(taskEntity.id, taskDto.commonUUID);

        return new ConstructionDto(taskEntity);
    }

//    public List<ConstructionDto> myTasks() {
//        String username = (String) authenticationFacade.getAuthentication().getPrincipal();
//        log.info("username -{}-", username);
//        UserEntity user = userRepository.findByUsername(username);
//        List<ConstructionEntity> tasks = constructionRepository.findByOwnerId(user.id);
//        return tasks.stream().map(ConstructionDto::new).collect(Collectors.toList());
//    }

    public List<ConstructionDto> findAll() {
        List<ConstructionEntity> tasks = constructionRepository.findAll();
//        return tasks.stream().map(ConstructionDto::new).sorted(Comparator.comparing((ConstructionDto taskDto) -> taskDto.daysLeft)).collect(Collectors.toList());
        return tasks.stream().map(ConstructionDto::new).collect(Collectors.toList());
    }


    public ConstructionDto findById(Long id) {
        Optional<ConstructionEntity> construction = constructionRepository.findById(id);
        return construction.isPresent() ? new ConstructionDto(construction.get()) : null;
    }

    public List<ConstructionDto> getTasksConstructionsForLast7Days() {
        logger.info("get last 7 days task's constructions");
        LocalDate begin = LocalDate.now();
        begin = begin.minus(7, ChronoUnit.DAYS);
        LocalDate end = LocalDate.now();
        List<ConstructionEntity> constructions = constructionRepository.tasksConstructionsInDateRange(begin, end);
        return constructions.stream().distinct().map(ConstructionDto::new).collect(Collectors.toList());
    }
    public List<ConstructionDto> getTasksConstructionsForLastMonth() {
        logger.info("get last month task's constructions");
        LocalDate begin = LocalDate.now();
        begin = begin.minus(begin.lengthOfMonth(), ChronoUnit.DAYS);
        LocalDate end = LocalDate.now();
        List<ConstructionEntity> constructions = constructionRepository.tasksConstructionsInDateRange(begin, end);
        return constructions.stream().distinct().map(ConstructionDto::new).collect(Collectors.toList());
    }


}
