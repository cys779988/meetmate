package com.spring.log.service;

import org.springframework.stereotype.Service;

import com.spring.log.model.LogEntity;
import com.spring.log.repository.LogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
	
	private final LogRepository logRepository;
	
	public void addLog(LogEntity logEntity) {
		logRepository.save(logEntity);
	}
}
