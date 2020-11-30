package com.webuploader.demo.upload.vo;

/**
 * 
 * 文件MD5校验VO
 */
public class CheckMd5FileVO {

	/**
	 * 0：不分片，1：分片
	 */
	private Integer type;
	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 文件Md5（文件唯一表示）
	 */
	private String fileMd5;

	/**
	 * 当前分片下标
	 */
	private Long chunk;

	/**
	 * 文件大小（如果分片了，则是分片文件大小）
	 */
	private Long fileSize;

	private String formData;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public Long getChunk() {
		return chunk;
	}

	public void setChunk(Long chunk) {
		this.chunk = chunk;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
