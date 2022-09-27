package kz.hbscale.main.service;

import kz.hbscale.main.dto.DictionaryDto;
import kz.hbscale.main.repository.DictionaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictionaryService {

    private DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<DictionaryDto> findByKey(String key) {
        return this.dictionaryRepository.findByKey(key)
                .stream()
                .map(DictionaryDto::new)
                .collect(Collectors.toList());
    }
}
