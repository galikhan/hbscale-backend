package kz.hbscale.main.fileupload;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    @Value("${file.upload.path}")
    private String FILE_UPLOAD;
    private FileService fileService;
    private Logger log = LogManager.getLogger(FileController.class);
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String handleFileUpload(@RequestParam("file") List<MultipartFile> files) {

        String commonUUID = FileUtils.generateUUID();

        files.stream().forEach(file -> {

            try {

                byte[] bytes = file.getBytes();
                Path path = Paths.get(FILE_UPLOAD + commonUUID + "_" + file.getOriginalFilename());
                Files.write(path, bytes);

                FileDto dto = new FileDto();
                dto.commonUUID = commonUUID;
                dto.created = LocalDateTime.now();
                dto.filename = file.getOriginalFilename();
                dto.path = FILE_UPLOAD;

                fileService.save(dto, commonUUID);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return "{\"commonUUID\":  \"" + commonUUID + "\" }";
    }

    @GetMapping("/file/container/{container}")
    public List<FileDto> findByContainer(@PathVariable("container") Long container) {
        return fileService.findByContainer(container);
    }

//    @GetMapping("/file/{id}/preview")
//    public List<FileDto> preview(@PathVariable("id") Long id) {
//        return fileService.preview(id);
//    }


    @GetMapping("/file/{id}/preview")
    public ResponseEntity<Resource> getImage(@PathVariable("id") Long id) throws IOException {

        System.out.println("id " + id);
        FileDto dto = fileService.findById(id);
        if(dto != null) {

        }

        File file = new File(dto.path + dto.commonUUID + "_" + dto.filename);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));


        String ext = FileUtils.getExtension(dto.filename);
        System.out.println("ext " + ext);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/" + ext);
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dto.filename);

        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .body(resource);
    }
}
