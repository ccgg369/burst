package com.webuploader.demo.upload.controller;


import com.webuploader.demo.upload.service.ChunkUploadService;
import com.webuploader.demo.upload.vo.CheckMd5FileVO;
import com.webuploader.demo.upload.vo.UploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//import javax.ws.rs.FormParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("file")
public class FileController {

	@Autowired
	private ChunkUploadService chunkUploadService;

	@RequestMapping(value = "check", method = RequestMethod.POST)
	@ResponseBody
	public Object check(@RequestParam("form-data") CheckMd5FileVO md5FileVO) {
		return chunkUploadService.check(md5FileVO);
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(@RequestParam(value = "file") MultipartFile file ,
						 @RequestParam(value = "name") String name,
						 @RequestParam(value = "type") String type,
						 @RequestParam(value = "lastModifiedDate") Date lastModifiedDate,
						 @RequestParam(value = "size") Long size,
						 @RequestParam(value = "chunks",required = false) Long chunks,
						 @RequestParam(value = "chunk",required = false) Long chunk,
						 @RequestParam(value = "id") String id,
						 @RequestParam(value = "fileMd5")String fileMd5,
						 @RequestParam(value = "chunkSize",required = false)Long chunkSize) {
		UploadVO uploadVO = new UploadVO();
		uploadVO.setName(name);
		uploadVO.setChunk(chunk);
		uploadVO.setChunks(chunks);
		uploadVO.setSize(size);
		uploadVO.setChunkSize(chunkSize);
		uploadVO.setLastModifiedDate(lastModifiedDate);
		uploadVO.setType(type);
		uploadVO.setId(id);
		uploadVO.setFileMd5(fileMd5);
		Long getChunk = uploadVO.getChunk();
		if (getChunk == null) {// 没有分片
			return chunkUploadService.UnChunkUpload(file, uploadVO);
		} else {// 分片
			return chunkUploadService.ChunkUpload(file, uploadVO);
		}
	}
}
