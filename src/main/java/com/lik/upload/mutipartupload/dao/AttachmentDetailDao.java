package com.lik.upload.mutipartupload.dao;

import com.lik.upload.mutipartupload.bean.AttachmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * @Author lk
 */
public interface AttachmentDetailDao extends JpaRepository<AttachmentDetail,String> {
}
