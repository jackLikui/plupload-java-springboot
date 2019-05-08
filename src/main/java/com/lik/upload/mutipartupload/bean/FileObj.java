package com.lik.upload.mutipartupload.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author lk
 */
@Entity
@Table(name = "attachment")
public class FileObj {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private Integer chunkNum;
    @Column
    private String md5;
    @Column
    private String path;
    @Column
    private Integer ismerge;
    @Column
    private Integer chunk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChunkNum() {
        return chunkNum;
    }

    public void setChunkNum(Integer chunkNum) {
        this.chunkNum = chunkNum;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIsmerge() {
        return ismerge;
    }

    public void setIsmerge(Integer ismerge) {
        this.ismerge = ismerge;
    }

    public Integer getChunk() {
        return chunk;
    }

    public void setChunk(Integer chunk) {
        this.chunk = chunk;
    }

    public String toString(){
        return name+":"+chunkNum;
    }
}
