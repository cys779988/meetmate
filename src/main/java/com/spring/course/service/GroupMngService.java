package com.spring.course.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.exception.BusinessException;
import com.spring.common.model.ErrorCode;
import com.spring.common.util.AppUtil;
import com.spring.course.model.CourseEntity;
import com.spring.course.model.GroupDivideRequest;
import com.spring.course.model.GroupDto;
import com.spring.course.model.GroupEntity;
import com.spring.course.model.GroupID;
import com.spring.course.repository.CourseRepository;
import com.spring.course.repository.CourseRepositorySupport;
import com.spring.course.repository.GroupRepository;
import com.spring.course.repository.GroupRepositorySupport;
import com.spring.security.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GroupMngService {
	private CourseRepositorySupport courseRepositorySupport;
	private UserRepository userRepository;
	private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private GroupRepositorySupport groupRepositorySupport;
	ObjectMapper objectMapper;
	
	@Transactional
	public void apply(Long no) {
		CourseEntity courseEntity = checkApplyCourse(no);
		
		GroupID groupId = GroupID.builder()
				.course(courseEntity)
				.member(userRepository.findById(AppUtil.getUser()).get())
				.build();
		
		if(groupRepository.findById(groupId).isPresent()) {
			throw new BusinessException(ErrorCode.DUPLICATED_APPLY);
		}
		
		updateApplyCount(no);
		
		groupRepository.save(GroupEntity.builder()
										.id(groupId)
										.build());
	}
	
	private CourseEntity checkApplyCourse(Long no) {
		CourseEntity courseEntity = courseRepository.findById(no).get();
		if(courseEntity.getCurNum() == courseEntity.getMaxNum()) {
			throw new BusinessException(ErrorCode.EXCEED_APPLY);
		}
		return courseEntity;
	}
	
    private void updateApplyCount(Long no) {
		CourseEntity result = courseRepositorySupport.updateCurNum(no);
		if(result.getCurNum() > result.getMaxNum()) {
			throw new BusinessException(ErrorCode.EXCEED_APPLY);
		}
    }

	public Map<String, List<GroupDto>> getUsersInGroupById(Long no) {
		List<GroupDto> userList = groupRepositorySupport.findUsersInGroupById(no);
		
		return userList.stream().collect(Collectors.groupingBy(GroupDto::getAssignmentType));
	}
	
	@Transactional
	public void autoDivide(GroupDivideRequest request) {
		Iterator<String> cndIt = request.getDivConditions().iterator();
		
		List<Map<String, Object>> memberList = request.getMemberList();
		
		// 분배 조건으로 사용자 정렬
		while (cndIt.hasNext()) {
			String value = cndIt.next().toString();
			memberList.sort((o1,o2) -> {
				return o1.get(value).toString().compareTo(o2.get(value).toString());
			});
		}
		
		// 분배 방법으로 정렬된 사용자 각 분반에 배정
		divideUsers(request);
		
		List<GroupEntity> groupEntityList = objectMapper.convertValue(memberList, new TypeReference<List<GroupDto>>() {})
															.stream().map(GroupDto::toEntity).collect(Collectors.toList());
		
		groupRepository.saveAll(groupEntityList);
	}
	
	private void divideUsers(GroupDivideRequest request) {
		List<Map<String, Object>> memberList = request.getMemberList();
		int divNo = request.getDivNo();
		
		int [] nmprArr = new int[divNo];
		int size = memberList.size();
		int maxNmpr = size / divNo;
		int outNmpr = size % divNo;
		
		for (int i = 0; i < nmprArr.length; i++) {
			nmprArr[i] = maxNmpr;
			if(i < outNmpr) {
				nmprArr[i]++;
			}
		}
		
		int i = 0;
		if(request.getDivMethod() > 0) {
			for(Map<String, Object> map : memberList) {
				if(nmprArr[i]==0) {
					++i;
				}
				map.put("divNo", i+1);
				nmprArr[i]--;
			}
		} else {
			for(Map<String,Object> map : memberList) {
				if(i == divNo) {
					i = 0;
				}
				map.put("divNo", i+1);
				nmprArr[i]--;
				i++;
			}
		}
	}

	@Transactional
	public void resetDivide(Long no) {
		groupRepositorySupport.resetDivide(no);
	}

	@Transactional
	public void updateDivide(List<GroupDto> groupDtoList) {
		List<GroupEntity> groupEntityList = groupDtoList.stream().map(GroupDto::toEntity).collect(Collectors.toList());
		groupRepository.saveAll(groupEntityList);
	}
}
