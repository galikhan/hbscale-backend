package kz.hbscale.main.service;

import kz.hbscale.main.dto.TaskDto;
import kz.hbscale.main.enums.TaskStatus;
import kz.hbscale.main.fileupload.FileRepository;
import kz.hbscale.main.fileupload.FileService;
import kz.hbscale.main.model.PersonEntity;
import kz.hbscale.main.model.TaskEntity;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.repository.DictionaryRepository;
import kz.hbscale.main.repository.PersonRepository;
import kz.hbscale.main.repository.TaskRepository;
import kz.hbscale.main.repository.UserRepository;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import kz.hbscale.main.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private AuthenticationFacade authenticationFacade;
    private TaskRepository taskRepository;
    private DictionaryRepository dictionaryRepository;
    private UserRepository userRepository;
    private Logger log = LogManager.getLogger(TaskService.class);
    private PersonRepository personRepository;
    private FileService fileService;

    public TaskService(AuthenticationFacade authenticationFacade,
                       TaskRepository taskRepository,
                       DictionaryRepository dictionaryRepository,
                       UserRepository userRepository,
                       PersonRepository personRepository,
                       FileService fileService) {
        this.authenticationFacade = authenticationFacade;
        this.taskRepository = taskRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.fileService = fileService;
    }

    public TaskDto save(TaskDto taskDto) {

        String username = (String) authenticationFacade.getAuthentication().getPrincipal();
        log.info("username -{}-", username);
        TaskEntity taskEntity = new TaskEntity();
        if(taskDto.id != null) {
            Optional<TaskEntity> task = taskRepository.findById(taskDto.id);
            if(task.isPresent()) {
                taskEntity = task.get();
            }
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

//        Optional<DictionaryEntity> contractor = dictionaryRepository.findById(taskDto.contractor);
//        if (contractor.isPresent()) {
//            taskEntity.contractor = contractor.get();
//        }
//        Optional<DictionaryEntity> project = dictionaryRepository.findById(taskDto.project);
//        if (project.isPresent()) {
//            taskEntity.project = project.get();
//        }
//        Optional<DictionaryEntity> customer = dictionaryRepository.findById(taskDto.customer);
//        if (customer.isPresent()) {
//            taskEntity.customer = customer.get();
//        }

        PersonEntity supplier = new PersonEntity(taskDto.supplier.fullname, taskDto.supplier.phone);
        PersonEntity director = new PersonEntity(taskDto.director.fullname, taskDto.director.phone);
        PersonEntity other = new PersonEntity(taskDto.other.fullname, taskDto.other.phone);

        taskEntity.supplier = personRepository.save(supplier);
        taskEntity.director = personRepository.save(director);
        taskEntity.other = personRepository.save(other);

        UserEntity user = userRepository.findByUsername(username);
        log.info("user {}", user);
        taskEntity.owner = user;

        if(StringUtils.isNotEmpty(taskDto.status)) {
            taskEntity.status= TaskStatus.valueOf(taskDto.status);
        }

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        taskEntity.whenContact = LocalDate.parse(taskDto.whenContact, pattern);

        this.taskRepository.save(taskEntity);
        this.fileService.updateContainerByCommonUUID(taskEntity.id, taskDto.commonUUID);

        return new TaskDto(taskEntity);
    }

    public List<TaskDto> myTasks() {
        String username = (String) authenticationFacade.getAuthentication().getPrincipal();

        log.info("username -{}-", username);
        UserEntity user = userRepository.findByUsername(username);
        List<TaskEntity> tasks = taskRepository.findByOwnerId(user.id);
        return tasks.stream().map(TaskDto::new).sorted(Comparator.comparing((TaskDto taskDto) -> taskDto.daysLeft)).collect(Collectors.toList());
    }
}
