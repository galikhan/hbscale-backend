package kz.hbscale.main.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByCommonUUID(String uuid);

    List<FileEntity> findByContainer(Long container);

    @Transactional
    @Modifying
    @Query("update FileEntity u set u.isRemoved = true where u.id = :id")
    int remove(@Param(value = "id") Long id);

    List<FileEntity> findByContainerAndIsRemoved(Long container, boolean b);

//    @Modifying
//    @Query("update FileEntity u set u.container = :container where u.commonUUID = :commonUUID")
//    Integer updateFileContainer(@Param(value = "container") long container, @Param(value = "commonUUID") String commonUUID);
}
