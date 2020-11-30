package com.webuploader.demo.upload.vo;

import java.util.Date;

/**
 * 文件上传VO
 *
 */
public class UploadVO {

	/***
	 * 文件id WU_FILE_0
	 * 
	 */
	private String id;
	/**
	 * 文件名称 Beyond Compare.rar
	 */
	private String name;
	/**
	 * 类型 application/octet-stream
	 */
	private String type;
	/**
	 * 文件大小
	 */
	private Long size;

	/**
	 * 最后修改时间
	 */
	private Date lastModifiedDate;

	/**
	 * 分片片数
	 */
	private Long chunks;

	/**
	 * 当前分片标识
	 */
	private Long chunk;

	/**
	 * 分片设置大小
	 */
	private Long chunkSize;

	/**
	 * 表单数据
	 */
	private String formData;

	/**
	 * 文件Md5(文件的唯一标识)
	 */
	private String fileMd5;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getChunks() {
		return chunks;
	}

	public void setChunks(Long chunks) {
		this.chunks = chunks;
	}

	public Long getChunk() {
		return chunk;
	}

	public void setChunk(Long chunk) {
		this.chunk = chunk;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public Long getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(Long chunkSize) {
		this.chunkSize = chunkSize;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}
}
