package com.lik.upload.mutipartupload.controller;

import com.lik.upload.mutipartupload.bean.FileObj;
import com.lik.upload.mutipartupload.dao.AttachmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @ProjectName: mutipartupload
 * @Package: com.lik.upload.mutipartupload.controller
 * @ClassName: DownloadController
 * @Author: chinasoft.k.li
 * @Description: 下载的controller
 * @Date: 2019/5/7 13:52
 * @Version: 1.0
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private AttachmentDao attachmentDao;

    @GetMapping("/getFiles")
    public List<FileObj> getFiles() {
        List<FileObj> fileObjList = attachmentDao.findAll();
        return fileObjList;
    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> reponseFile(@PathVariable("id") String id, HttpServletResponse response) {
        FileObj fileObj = attachmentDao.getOne(id);
        String localPath = fileObj.getPath();
        try{
            //将该文件加入到输入流之中
            InputStream in=new FileInputStream(new File(localPath+"/"+fileObj.getName()));
            byte[] body = null;
            // 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
            body=new byte[in.available()];
            //读入到输入流里面
            in.read(body);
            //设置响应头
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename="+fileObj.getName());
            //设置响应吗
            HttpStatus statusCode = HttpStatus.OK;
            return new ResponseEntity<byte[]>(body, headers, statusCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
