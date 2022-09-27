package kz.hbscale.main.controller;

import kz.hbscale.main.dto.DictionaryDto;
import kz.hbscale.main.service.DictionaryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class DictionaryController {

    private Logger logger
            = LogManager.getLogger(DictionaryController.class);
    private DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/dictionary/{key}")
    public List<DictionaryDto> findByKey(@PathVariable String key) {
        logger.info("key {}", key);
        return this.dictionaryService.findByKey(key);
    }
}
