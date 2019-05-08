package com.lik.upload.mutipartupload.mapper;

import com.lik.upload.mutipartupload.bean.FileObj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author lk
 */
@Mapper
public interface UploadMapper {

    List<Map> findAll();

    void insert(FileObj fileObj);

    List<FileObj> findNotMerges();

    int findCount(String id);

    void setMergeComplete(String id);

    FileObj findOneById(String id);

    void upLoadChunk(FileObj fileObj);
}
