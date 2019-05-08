package com.lik.upload.mutipartupload.dao;

import com.lik.upload.mutipartupload.bean.FileObj;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @Author lk
 */
public interface AttachmentDao extends JpaRepository<FileObj,String> {
}
