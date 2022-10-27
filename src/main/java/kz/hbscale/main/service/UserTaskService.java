package kz.hbscale.main.service;

import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.dto.UserTaskDto;
import kz.hbscale.main.enums.TaskStatus;
import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.model.UserTaskEntity;
import kz.hbscale.main.repository.ConstructionRepository;
import kz.hbscale.main.repository.DictionaryRepository;
import kz.hbscale.main.repository.UserRepository;
import kz.hbscale.main.repository.UserTaskRepository;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<ResultDto> getResults() {
        return userTaskRepository.getResults();
    }


    public UserTaskDto save(UserTaskDto dto) {

        String username = (String) authenticationFacade.getAuthentication().getPrincipal();

        UserTaskEntity entity   =  new UserTaskEntity();
        entity.created = LocalDateTime.now();
        entity.description = dto.description;
        entity.isRemoved  = false;
        entity.status   = TaskStatus.waiting;

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

    public List<UserTaskDto> myTasks() {
        String username = (String) authenticationFacade.getAuthentication().getPrincipal();
        UserEntity user = userRepository.findByUsername(username);
        List<UserTaskEntity>  tasks = userTaskRepository.findByOwnerId(user.id);
        return tasks.stream().map(UserTaskDto::new).collect(Collectors.toList());
    }
}
