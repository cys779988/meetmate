package com.spring.file.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.spring.common.exception.BusinessException;
import com.spring.common.model.ErrorCode;
import com.spring.file.model.FileDirectory;
import com.spring.file.model.FileDto;
import com.spring.file.model.FileExtnFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileHandler {
	
	private static String FILE_PATH;
	
	private static ResourceLoader resourceLoader;
	
	@Autowired
	private FileHandler(@Value("${file.upload.path}") String filePath, ResourceLoader resourceLoader) {
		FILE_PATH = filePath;
		FileHandler.resourceLoader = resourceLoader;
	}
    
	public static void deleteFiles(List<FileDto> fileList) {
		for(FileDto file : fileList) {
			String filePath = file.getFilePath() + file.getSaveFileName();
			Path path = Paths.get(filePath);
			
			try {
				Files.deleteIfExists(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Resource loadFileAsResource(FileDto fileDto) {
		Path filePath = Paths.get(fileDto.getFilePath() + fileDto.getSaveFileName());
		Resource resource = resourceLoader.getResource("file:" + filePath);
		if(!resource.exists()) {
			throw new BusinessException(ErrorCode.NOTFOUND_FILE);
		}
		
		return resource;
	}
	
	public static List<FileDto> uploadFileAndParseFileInfo(List<MultipartFile> mpFileList, FileDirectory fileDir) {
		List<FileDto> result = new ArrayList<>();
		
		String saveFilePath = FILE_PATH + fileDir.getDirName();
		
		File file = new File(saveFilePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		for (MultipartFile multipartFile : mpFileList) {
			String originalFileName = multipartFile.getOriginalFilename();
			String extnName = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
			
			if(!fileExtnCheck(extnName)) {
				throw new BusinessException(ErrorCode.INVALID_FILE_EXTN);
			}
			
			String saveFileName = createFileName(saveFilePath, extnName);
			
			file = new File(saveFilePath + saveFileName);
			
			try {
				multipartFile.transferTo(file);
				
				FileDto fileDto = FileDto.builder()
						.filePath(saveFilePath)
						.fileSize(multipartFile.getSize())
						.originalFileName(originalFileName)
						.saveFileName(saveFileName)
						.build();
				
				result.add(fileDto);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
			}
			
		}
		
		return result;
	}
	
	private static boolean fileExtnCheck(String extnName) {
		for(FileExtnFilter name : FileExtnFilter.values()) {
			if(name.getExtnName().equals(extnName)) {
				return false;
			}
		}
		return true;
	}
	
	private static String createFileName(String filePath, String extnNm) {
		String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		return UUID.randomUUID() + "_" + dateFormat + '.' + extnNm;
	}
}
