package kz.hbscale.main.service;

import kz.hbscale.main.dto.TaskDto;
import kz.hbscale.main.model.DictionaryEntity;
import kz.hbscale.main.model.PersonEntity;
import kz.hbscale.main.model.TaskEntity;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.repository.DictionaryRepository;
import kz.hbscale.main.repository.PersonRepository;
import kz.hbscale.main.repository.TaskRepository;
import kz.hbscale.main.repository.UserRepository;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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

    public TaskService(
            AuthenticationFacade authenticationFacade,
            TaskRepository taskRepository,
            DictionaryRepository dictionaryRepository,
            UserRepository userRepository,
            PersonRepository personRepository) {
        this.authenticationFacade = authenticationFacade;
        this.taskRepository = taskRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    public TaskDto save(TaskDto taskDto) {

        String username = (String) authenticationFacade.getAuthentication().getPrincipal();
        log.info("username -{}-", username);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.address = taskDto.address;
        taskEntity.name = taskDto.name;
        taskEntity.fromQuarter = taskDto.from.quarter;
        taskEntity.toQuarter = taskDto.to.quarter;
        taskEntity.fromYear = taskDto.from.year;
        taskEntity.toYear = taskDto.to.year;
        taskEntity.lat = taskDto.lat;
        taskEntity.lng = taskDto.lng;
        Optional<DictionaryEntity> contractor = dictionaryRepository.findById(taskDto.contractor.id);
        if (contractor.isPresent()) {
            taskEntity.contractor = contractor.get();
        }
        Optional<DictionaryEntity> project = dictionaryRepository.findById(taskDto.project.id);
        if (project.isPresent()) {
            taskEntity.project = project.get();
        }
        Optional<DictionaryEntity> customer = dictionaryRepository.findById(taskDto.customer.id);
        if (customer.isPresent()) {
            taskEntity.customer = customer.get();
        }

        PersonEntity supplier = new PersonEntity(taskDto.supplier.fullname, taskDto.supplier.phone);
        PersonEntity director = new PersonEntity(taskDto.director.fullname, taskDto.director.phone);
        PersonEntity other = new PersonEntity(taskDto.other.fullname, taskDto.other.phone);

        taskEntity.supplier = personRepository.save(supplier);
        taskEntity.director = personRepository.save(director);
        taskEntity.other = personRepository.save(other);

        UserEntity user = userRepository.findByUsername(username);
        log.info("user {}", user);
        taskEntity.owner = user;

        this.taskRepository.save(taskEntity);
        return new TaskDto(taskEntity);
    }

    public List<TaskDto> myTasks() {
        String username = (String) authenticationFacade.getAuthentication().getPrincipal();

        log.info("username -{}-", username);
        UserEntity user = userRepository.findByUsername(username);
        List<TaskEntity> tasks = taskRepository.findByOwner(user);
        return tasks.stream().map(TaskDto::new).collect(Collectors.toList());
    }
}
