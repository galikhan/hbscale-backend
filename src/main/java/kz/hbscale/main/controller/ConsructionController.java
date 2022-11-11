package kz.hbscale.main.controller;

import kz.hbscale.main.dto.ConstructionDto;
import kz.hbscale.main.dto.ResultDto;
import kz.hbscale.main.model.ConstructionEntity;
import kz.hbscale.main.security.facade.AuthenticationFacade;
import kz.hbscale.main.service.ConstructionService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class ConsructionController {

    private ConstructionService service;
    private AuthenticationFacade authenticationFacade;


    public ConsructionController(ConstructionService constructionService, AuthenticationFacade authenticationFacade) {
        this.service = constructionService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/construction/all")
    public List<ConstructionDto> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/construction/id/{id}")
    public ConstructionDto findById(@PathVariable(name="id") Long id) {
        return this.service.findById(id);
    }

    @PostMapping("/construction")
    public ConstructionDto save(@RequestBody ConstructionDto dto) {
        System.out.println("getPrincipal:" + authenticationFacade.getAuthentication().getPrincipal());
        return service.save(dto);
    }


    @GetMapping("/construction/tasks/last7days")
    public List<ConstructionDto> getLastSevenDays() {
        return this.service.getTasksConstructionsForLast7Days();
    }

    @GetMapping("/construction/tasks/monthly")
    public List<ConstructionDto> getMonthlyResults() {
        return this.service.getTasksConstructionsForLastMonth();
    }

}
