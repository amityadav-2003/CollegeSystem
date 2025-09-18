package com.amstech.std.system.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

	private final String BASE_REPO_DIRECTORY_PATH = "./storage";

	// {baseRepoPath}/{directory}/{fileName}.{fielExt};
	private final String FILE_PATH_FORMAT = "{0}/{1}/{2}.{3}";

	@Getter
	@Value("${app.max-file-size}")
	private long fileMaxSize;

	@PostConstruct
	public void init() {
		log.info("Init fileService..");
		File file = new File(BASE_REPO_DIRECTORY_PATH);
		if (!file.exists()) {
			try {
				log.info("Base repo does not exist, creating base repo directory..");
				if (file.mkdir())
					log.info("Base repo created successfuly.");
				else
					log.info("Unable to create base repo.");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed to create base repo due to: [{}]", e.getMessage(), e);
			}
		} else {
			log.info("Base repo already exist.");
		}

		log.info("Max file size: {}", getFileMaxSize());
	}

	public String saveFile(byte[] data, String directoryName, String fileExt) throws IOException {

		String filePath = MessageFormat.format(FILE_PATH_FORMAT, BASE_REPO_DIRECTORY_PATH, directoryName,
				UUID.randomUUID().toString(), fileExt);

		FileUtils.writeByteArrayToFile(new File(filePath), data);
		log.info("Saved file on the path: {}", filePath);
		return filePath;

	}

}
