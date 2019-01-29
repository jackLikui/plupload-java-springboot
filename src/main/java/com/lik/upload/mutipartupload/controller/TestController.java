package com.lik.upload.mutipartupload.controller;

import com.lik.upload.mutipartupload.bean.AttachmentDetail;
import com.lik.upload.mutipartupload.bean.FileObj;
import com.lik.upload.mutipartupload.dao.AttachmentDao;
import com.lik.upload.mutipartupload.dao.AttachmentDetailDao;
import com.lik.upload.mutipartupload.mapper.AttachmentMapper;
import com.lik.upload.mutipartupload.mapper.UploadMapper;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Controller
public class TestController {

    @Value("${uploadPath}")
    private String uploadPath;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private AttachmentDetailDao attachmentDetailDao;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file,Integer chunk,Integer chunks,String name,String md5){
        try {
            File filePath = getPathFile(md5);
            File desfile = new File(filePath,chunk.toString());
            desfile.setReadable(true);
            desfile.setWritable(true);
            desfile.setExecutable(true);
            if(!filePath.exists()){
                filePath.mkdirs();
            }
            desfile.createNewFile();
            FileObj fileObj = new FileObj();
            fileObj.setName(name);
            fileObj.setIsmerge(0);
            fileObj.setChunkNum(chunks);
            fileObj.setMd5(md5);
            fileObj.setChunk(chunk);
            file.transferTo(desfile);
            //文件信息保存
            saveBaseInfo2DS(filePath, fileObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1";
    }

    @PostMapping("offset")
    public ResponseEntity offset(String md5,String name){
        String[] idArr = {md5};
        List<FileObj> allById = attachmentDao.findAllById(Arrays.asList(idArr));
        ResponseEntity<Object> resultEntity = null;
        if(allById != null && allById.size() != 0){
            FileObj fileObj = allById.get(0);
            if(fileObj.getIsmerge() == 1){
                resultEntity = ResponseEntity.ok("100");
            }else{
                Double dChunk = Double.valueOf(fileObj.getChunk());
                Double Dchunks = Double.valueOf(fileObj.getChunkNum());
                Double percent = dChunk/Dchunks * 100;
                resultEntity = ResponseEntity.ok(percent);
            }
        }else{
            resultEntity = ResponseEntity.ok("0");
        }
        //每次上传时检查下当前名称和md5相同的文件是否存在
        AttachmentDetail attachmentDetail = new AttachmentDetail();
        attachmentDetail.setName(name);
        attachmentDetail.setMd5(md5);
        Example<AttachmentDetail> exple = Example.of(attachmentDetail);
        Optional<AttachmentDetail> attachmentOp = attachmentDetailDao.findOne(exple);
        if(!attachmentOp.isPresent()){
            attachmentDetail.setId(UUID.randomUUID().toString().replace("-",""));
            attachmentDetailDao.save(attachmentDetail);
        }
        return resultEntity;
    }

    private void saveBaseInfo(File filePath, FileObj fileObj) throws IOException {
        File fileInfo = new File(filePath,"/fileInfo.txt");
        if(!fileInfo.exists()){
            fileInfo.createNewFile();
            BufferedOutputStream bufferedOutputStream = null;
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileInfo));
                bufferedOutputStream.write(fileObj.toString().getBytes());
                bufferedOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
                bufferedOutputStream.close();
            }
        }
    }

    private void saveBaseInfo2DS(File filePath,FileObj fileObj){
        Optional<FileObj> byId = attachmentDao.findById(fileObj.getMd5());
        fileObj.setId(fileObj.getMd5());
        fileObj.setPath(filePath.getPath());
        if(!byId.isPresent()){
            attachmentDao.save(fileObj);
        }else {
            if(fileObj.getChunkNum() == fileObj.getChunk()+1){
                fileObj.setIsmerge(2);
            }
            attachmentDao.save(fileObj);
        }
    }


    public File getPathFile(String md5){
        String firstPath = md5.substring(0, 2);
        String secondPath = md5.substring(2, 4);
        String finalPath = md5;
        return new File(uploadPath+firstPath+"/"+secondPath+"/"+finalPath);
    }



    public static void main(String[] args) throws Exception{
//        Integer lenth = 12;
//        File file = new File("G:\\temp");
//        String md5 = "2bd96d8f160b9433544a2300300499fc";
//        File filePath = getPathFile(md5);
//        File fileInfo = new File(filePath,"fileInfo.txt");
//        String resultFile = filePath.getPath();
//        try{
//            BufferedReader reader = new BufferedReader(new FileReader(fileInfo));
//            String s = reader.readLine();
//            String[] split = s.split(":");
//            resultFile += split[0];
//            lenth = Integer.valueOf(split[1]);
//            reader.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        File[] fpaths =  new File[lenth];
//        for (Integer i=0;i<lenth;i++){
//            fpaths[i] = new File(filePath,i.toString());
//        }
//        try {
//            int bufSize = 1024;
//            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFile));
//            byte[] buffer = new byte[bufSize];
//
//            for (int i = 0; i < fpaths.length; i ++) {
//                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fpaths[i]));
//                int readcount;
//                while ((readcount = inputStream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, readcount);
//                }
//                inputStream.close();
//            }
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
