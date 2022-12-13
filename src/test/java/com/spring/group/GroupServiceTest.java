package com.spring.group;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.common.exception.BusinessException;
import com.spring.common.model.ErrorCode;
import com.spring.common.util.AppUtil;
import com.spring.course.model.CourseEntity;
import com.spring.course.model.GroupEntity;
import com.spring.course.repository.CourseRepository;
import com.spring.course.repository.CourseRepositorySupport;
import com.spring.course.repository.GroupRepository;
import com.spring.course.repository.GroupRepositorySupport;
import com.spring.course.service.GroupMngService;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;
import com.spring.security.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
	
	@InjectMocks
	private GroupMngService groupMngService;

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private CourseRepositorySupport courseRepositorySupport;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
    private GroupRepository groupRepository;
	
    @Mock
    private GroupRepositorySupport groupRepositorySupport;
    
    private static Long ID = 1L;
    
    private CourseEntity getCourseData() {
    	String node = "[{\"data\":{\"id\":\"Main\",\"name\":\"Main\",\"content\":\"Main\",\"shape\":\"round-hexagon\",\"width\":80,\"height\":40,\"font\":7,\"color\":\"#D0C1B2\"},\"renderedPosition\":{\"x\":-6.666666666666669,\"y\":37.333333333333336}}]";
    	String edge = "[]";
    	UserEntity user =  UserEntity.builder()
				.email("cys779988@naver.com")
				.name("채영수")
				.role(Role.GUEST)
				.password("1234")
				.build();
    	
    	CourseEntity course = CourseEntity.builder()
    			.id(ID)
    			.registrant(user)
    			.title("테스트제목")
    			.category(1L)
    			.content("테스트글")
    			.curNum(1)
    			.maxNum(20)
    			.divclsNo(2)
    			.node(node)
    			.edge(edge)
    			.build();
    	return course;
	}

	@Test
	public void exceedApplyExceptionTest() {
		CourseEntity courseEntity = getCourseData();
		courseEntity.setMaxNum(1);

		when(courseRepository.findById(ID)).thenReturn(Optional.of(courseEntity));

		try (MockedStatic<AppUtil> appUtil = Mockito.mockStatic(AppUtil.class)) {
			when(AppUtil.getUser()).thenReturn("user");
			lenient().when(groupRepository.findById(any())).thenReturn(Optional.of(GroupEntity.builder().build()));
			lenient().when(courseRepositorySupport.updateCurNum(ID)).thenReturn(courseEntity);
			Throwable exception = assertThrows(BusinessException.class, () -> {
				groupMngService.apply(ID);
			});

			assertThat(exception.getMessage(), is(ErrorCode.EXCEED_APPLY.getMessage()));

		}

	}

	@Test
	public void duplicatedApplyExceptionTest() {
		CourseEntity courseEntity = getCourseData();

		when(courseRepository.findById(ID)).thenReturn(Optional.of(courseEntity));

		try (MockedStatic<AppUtil> appUtil = Mockito.mockStatic(AppUtil.class)) {
			when(userRepository.findById(AppUtil.getUser())).thenReturn(Optional.of(UserEntity.builder().build()));
			lenient().when(groupRepository.findById(any())).thenReturn(Optional.of(GroupEntity.builder().build()));
			lenient().when(courseRepositorySupport.updateCurNum(ID)).thenReturn(courseEntity);
			Throwable exception = assertThrows(BusinessException.class, () -> {
				groupMngService.apply(ID);
			});
			
			assertThat(exception.getMessage(), is(ErrorCode.DUPLICATED_APPLY.getMessage()));
		}
	}
}
