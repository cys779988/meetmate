package com.spring.course.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void autoDivide(Map<String, Object> param) {

		List<String> cndList = (List<String>)param.get("divCnd");
		Iterator<String> cndIt = cndList.iterator();
		
		List<Map<String, Object>> divUserList = (List<Map<String, Object>>)param.get("data");
		while (cndIt.hasNext()) {
			String value = cndIt.next().toString();
			divUserList.sort(new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return o1.get(value).toString().compareTo(o2.get(value).toString());
				}
			});
		}
		int mth = (int)param.get("divMth");
		int divNo = (int)param.get("divNo");
		
		divUser(divUserList, mth, divNo);
		
		List<GroupDto> groupDtoList = objectMapper.convertValue(divUserList, new TypeReference<List<GroupDto>>() {});
		List<GroupEntity> groupEntityList = new ArrayList<>();
		
		groupDtoList.stream().forEach(dto -> {
			groupEntityList.add(dto.toEntity());
		});
		
		groupRepository.saveAll(groupEntityList);
	}
	
	private void divUser(List<Map<String, Object>> divUserList, int mth, int divNo) {
		
		int maxNmpr = divUserList.size() / divNo;
		int outNmpr = divUserList.size() % divNo;
		
		int [] nmprArr = new int[divNo];
		
		for (int i = 0; i < nmprArr.length; i++) {
			nmprArr[i] = maxNmpr;
			if(i < outNmpr) {
				nmprArr[i]++;
			}
		}
		int i = 0;
		if(mth > 0) {
			for(Map<String, Object> map : divUserList) {
				if(nmprArr[i]==0) {
					++i;
				}
				map.put("divNo", i+1);
				nmprArr[i]--;
			}
		} else {
			for(Map<String,Object> map : divUserList) {
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
		List<GroupEntity> groupEntityList = new ArrayList<>();
		groupDtoList.stream().forEach(dto -> {
			groupEntityList.add(dto.toEntity());
		});
		groupRepository.saveAll(groupEntityList);
	}
	
	@SuppressWarnings("rawtypes")
	public static Object convertMapToObject(Map map, Object objClass) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			try {
				Method[] methods = objClass.getClass().getDeclaredMethods();
				for (int i = 0; i <= methods.length - 1; i++) {
					if (methodString.equals(methods[i].getName())) {
						System.out.println("invoke : " + methodString);
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return objClass;
	}
}
