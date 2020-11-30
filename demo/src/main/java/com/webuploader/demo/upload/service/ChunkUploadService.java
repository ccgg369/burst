package com.webuploader.demo.upload.service;


import com.webuploader.demo.upload.util.FileUtil;
import com.webuploader.demo.upload.util.RETURN;
import com.webuploader.demo.upload.vo.CheckMd5FileVO;
import com.webuploader.demo.upload.vo.UploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ChunkUploadService {

	private static Logger LOG = LoggerFactory.getLogger(ChunkUploadService.class);

	@Value("${file.upload.path}")
	private String UPLOAD_PATH;

	private static final String Delimiter = "-";

	/**
	 * 上传之前校验(整个文件、分片)
	 * 
	 * @param md5FileVO
	 * @return
	 */
	public Object check(CheckMd5FileVO md5FileVO) {
		Integer type = md5FileVO.getType();
		Long chunk = md5FileVO.getChunk();
		String fileName = md5FileVO.getFileName();
		Long fileSize = md5FileVO.getFileSize();
		if (type == 0) {// 未分片校验
			String destfilePath = UPLOAD_PATH + File.separator + fileName;
			File destFile = new File(destfilePath);
			if (destFile.exists() && destFile.length() == fileSize) {
				return RETURN.success("文件已存在，跳过", 1);
			} else {
				return RETURN.success("文件不存在", 0);
			}
		} else {// 分片校验
			String fileMd5 = md5FileVO.getFileMd5();
			String destFileDir = UPLOAD_PATH + File.separator + fileMd5;
			String destFileName = chunk + Delimiter + fileName;
			String destFilePath = destFileDir + File.separator + destFileName;
			File destFile = new File(destFilePath);
			if (destFile.exists() && destFile.length() == fileSize) {
				return RETURN.success("分片已存在，跳过", 1);
			} else {
				return RETURN.success("分片不存在", 0);
			}
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param uploadVO
	 * @param appVersion
	 * @return
	 */
	public Object upload(MultipartFile file, UploadVO uploadVO) {
		Long chunk = uploadVO.getChunk();
		if (chunk == null) {// 没有分片
			return UnChunkUpload(file, uploadVO);
		} else {// 分片
			return ChunkUpload(file, uploadVO);
		}
	}

	/**
	 * 分片上传
	 * 
	 * @param file
	 * @param uploadVO
	 * @param appVersion
	 * @return
	 */
	public Object ChunkUpload(MultipartFile file, UploadVO uploadVO) {
		String fileName = uploadVO.getName();
		String fileMd5 = uploadVO.getFileMd5();
		Long chunk = uploadVO.getChunk();// 当前片
		Long chunks = uploadVO.getChunks();// 总共多少片

		// 分片目录创建
		String chunkDirPath = UPLOAD_PATH + File.separator + fileMd5;
		File chunkDir = new File(chunkDirPath);
		if (!chunkDir.exists()) {
			chunkDir.mkdirs();
		}
		// 分片文件上传
		String chunkFileName = chunk + Delimiter + fileName;
		String chunkFilePath = chunkDir + File.separator + chunkFileName;
		File chunkFile = new File(chunkFilePath);
		try {
			file.transferTo(chunkFile);
		} catch (Exception e) {
			LOG.error("分片上传出错", e);
			return RETURN.fail("分片上传出错", 1);
		}
		// 合并分片
		Long chunkSize = uploadVO.getChunkSize();
		long seek = chunkSize * chunk;
		String destFilePath = UPLOAD_PATH + File.separator + fileName;
		File destFile = new File(destFilePath);
		if (chunkFile.length() > 0) {
			try {
				FileUtil.randomAccessFile(chunkFile, destFile, seek);
			} catch (IOException e) {
				LOG.error("分片{}合并失败：{}", chunkFile.getName(), e.getMessage());
				return RETURN.fail("分片合并失败", 1);
			}
		}
		if (chunk == chunks - 1) {
			// 删除分片文件夹
			FileUtil.deleteDirectory(chunkDirPath);

			return RETURN.success("上传成功", 1);
		} else {
			return RETURN.fail("上传中...", 1);
		}
	}

	/**
	 * 未分片上传
	 * 
	 * @param file
	 * @param uploadVO
	 * @param appVersion
	 * @return
	 */
	public Object UnChunkUpload(MultipartFile file, UploadVO uploadVO) {
		String fileName = uploadVO.getName();
		// String fileMd5 = uploadVO.getFileMd5();
		// 文件上传
		File destFile = new File(UPLOAD_PATH + File.separator + fileName);
		if (file != null && !file.isEmpty()) {
			// 上传目录
			File fileDir = new File(UPLOAD_PATH);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			if (destFile.exists()) {
				destFile.delete();
			}
			try {
				file.transferTo(destFile);
				return RETURN.success("上传成功", 0);
			} catch (Exception e) {
				LOG.error("文件上传出错", e);
				return RETURN.fail("文件上传出错", 0);
			}
		}
		return RETURN.fail("上传失败", 0);
	}
}
