package kz.hbscale.main.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByCommonUUID(String uuid);

    List<FileEntity> findByContainer(Long container);

//    @Modifying
//    @Query("update FileEntity u set u.container = :container where u.commonUUID = :commonUUID")
//    Integer updateFileContainer(@Param(value = "container") long container, @Param(value = "commonUUID") String commonUUID);
}
