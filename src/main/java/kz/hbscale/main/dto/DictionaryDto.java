package kz.hbscale.main.dto;

import kz.hbscale.main.model.DictionaryEntity;

public class DictionaryDto {

    public Long id;
    public String key;
    public String code;
    public String name;

    public DictionaryDto() {
    }

    public DictionaryDto(DictionaryEntity dictionaryEntity) {
        this.id = dictionaryEntity.id;
        this.name = dictionaryEntity.name;
        this.code = dictionaryEntity.code;
        this.key = dictionaryEntity.key;
    }
}
