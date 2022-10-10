package kz.hbscale.main.fileupload;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileDto save(FileDto file, String commonUUID) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.commonUUID = commonUUID;
        fileEntity.containerType = ContainerTypEnum.TASK;
        fileEntity.filename = file.filename;
        fileEntity.path = file.path;
        fileRepository.save(fileEntity);
        return new FileDto(fileEntity);
    }

    public List<FileEntity> findByCommonUUID(String uuid) {
        return fileRepository.findByCommonUUID(uuid);
    }

    public void updateContainerByCommonUUID(Long id, String uuid) {
        List<FileEntity> fileEntities = findByCommonUUID(uuid);
        fileEntities.stream().forEach(file -> {
            file.container = id;
            fileRepository.save(file);
        });
    }

    public List<FileDto> findByContainer(Long container) {
        return fileRepository.findByContainer(container).stream().map(FileDto::new).collect(Collectors.toList());
    }

    public FileDto findById(Long id) {
        Optional<FileEntity> file = fileRepository.findById(id);
        if(file.isPresent()) {
            return new FileDto(file.get());
        }
        return null;
    }
}
