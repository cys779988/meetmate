package com.spring.course;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.common.model.PageVO;
import com.spring.course.model.CategoryEntity;
import com.spring.course.model.CourseDto;
import com.spring.course.model.CourseEntity;
import com.spring.course.repository.CourseRepository;
import com.spring.course.repository.CourseRepositorySupport;
import com.spring.course.service.CourseService;
import com.spring.security.model.Role;
import com.spring.security.model.UserEntity;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
	
	@InjectMocks
	private CourseService courseService;

	@Spy
	private ObjectMapper objectMapper;
	
    @Mock
    private CourseRepository courseRepository;	
	
    @Mock
    private CourseRepositorySupport courseRepositorySupport;	
    
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
    			.id(1L)
    			.registrant(user)
    			.title("테스트제목")
    			.category(CategoryEntity.builder().id(1L).build())
    			.content("테스트글")
    			.curNum(1)
    			.maxNum(20)
    			.divclsNo(2)
    			.node(node)
    			.edge(edge)
    			.build();
    	return course;
    }
    
    private List<CourseEntity> getCourseListData() {
    	String node = "[{\"data\":{\"id\":\"Main\",\"name\":\"Main\",\"content\":\"Main\",\"shape\":\"round-hexagon\",\"width\":80,\"height\":40,\"font\":7,\"color\":\"#D0C1B2\"},\"renderedPosition\":{\"x\":-6.666666666666669,\"y\":37.333333333333336}}]";
    	String edge = "[]";
    	
    	UserEntity user =  UserEntity.builder()
    							.email("admin")
    							.name("채영수")
    							.role(Role.GUEST)
    							.password("")
    							.build();
    	
    	CategoryEntity category = CategoryEntity.builder().id(1L).build();
    	List<CourseEntity> courseList = Arrays.asList(
    			CourseEntity.builder().id(1L).registrant(user).title("테스트제목1").category(category).content("테스트글").curNum(1).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(2L).registrant(user).title("테스트제목2").category(category).content("테스트글").curNum(3).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(3L).registrant(user).title("테스트제목3").category(category).content("테스트글").curNum(4).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(4L).registrant(user).title("테스트제목4").category(category).content("테스트글").curNum(2).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(5L).registrant(user).title("테스트제목5").category(category).content("테스트글").curNum(5).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(6L).registrant(user).title("테스트제목6").category(category).content("테스트글").curNum(7).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(7L).registrant(user).title("테스트제목7").category(category).content("테스트글").curNum(9).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(8L).registrant(user).title("테스트제목8").category(category).content("테스트글").curNum(6).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(9L).registrant(user).title("테스트제목9").category(category).content("테스트글").curNum(5).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(10L).registrant(user).title("테스트제목10").category(category).content("테스트글").curNum(1).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(11L).registrant(user).title("테스트제목11").category(category).content("테스트글").curNum(2).maxNum(20).divclsNo(2).node(node).edge(edge).build(),
    			CourseEntity.builder().id(12L).registrant(user).title("테스트제목12").category(category).content("테스트글").curNum(3).maxNum(20).divclsNo(2).node(node).edge(edge).build()
    	);
    	
    	return courseList;
    }
    
    private List<Object> jsonToList(String data) {
    	List<Object> readValue = new ArrayList<Object>();
    	try {
    		readValue = objectMapper.readValue(data, new TypeReference<List<Object>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readValue;
    }
    
    @Test
    public void getCourses() {
    	PageVO pageVO = new PageVO(1, 10);
    	Page<CourseEntity> page = new PageImpl<CourseEntity>(getCourseListData());
    	
    	when(courseRepository.findAll(any(PageRequest.class))).thenReturn(page);
    	
    	List<CourseDto> courseList = courseService.getCourses(pageVO, null);
    	
    	assertThat(courseList.get(0), instanceOf(CourseDto.class));
    	
    	verify(courseRepository, times(1)).findAll(any(PageRequest.class));
    }
    
    @Test
    public void getCoursesByCategoryId() {

    	PageVO pageVO = new PageVO(1, 10);
    	Page<CourseEntity> page = new PageImpl<CourseEntity>(getCourseListData());
    	Long id = 1L;
    	when(courseRepository.findByCategoryId(any(PageRequest.class), any(Long.class))).thenReturn(page);

    	List<CourseDto> courseList = courseService.getCourses(pageVO, id);
    	
    	assertThat(courseList.get(0), notNullValue());
    	
    	verify(courseRepository, times(1)).findByCategoryId(any(PageRequest.class), any(Long.class));
    }
    
    @Test
    public void getCourse() {
    	String node = "[{\"data\":{\"id\":\"Main\",\"name\":\"Main\",\"content\":\"Main\",\"shape\":\"round-hexagon\",\"width\":80,\"height\":40,\"font\":7,\"color\":\"#D0C1B2\"},\"renderedPosition\":{\"x\":-6.666666666666669,\"y\":37.333333333333336}}]";
    	String edge = "[]";
    	UserEntity user =  UserEntity.builder()
				.email("cys779988@naver.com")
				.name("채영수")
				.role(Role.GUEST)
				.password("")
				.build();
    	
    	Optional<CourseEntity> course = Optional.of(CourseEntity.builder().id(1L).registrant(user).title("테스트제목1").category(CategoryEntity.builder().id(1L).build()).content("테스트글").curNum(1).maxNum(20).divclsNo(2).node(node).edge(edge).build());
    	
    	when(courseRepository.findById(1L)).thenReturn(course);
    	
    	CourseDto CourseDto = courseService.getCourse(1L);
    	
    	assertThat(CourseDto, notNullValue());
    	
    	verify(courseRepository, times(1)).findById(1L);
    }
    
    @Test
    public void getCourseCount() {
    	when(courseRepository.count()).thenReturn(12L);
    	
    	Long boardSize = courseService.getCourseCount(null);
    	
    	assertThat(boardSize, is(12L));
    	
    	verify(courseRepository, times(1)).count();
    }
    
    @Test
    public void getCourseCountByTitle() {
    	when(courseRepository.countByCategoryId(any(Long.class))).thenReturn(12L);
    	
    	Long boardSize = courseService.getCourseCount(1L);
    	
    	assertThat(boardSize, is(12L));
    	
    	verify(courseRepository, times(1)).countByCategoryId(any(Long.class));
    }
    
    @Test
    public void getCourseByUser() {
    	
    	List<CourseEntity> courseList = getCourseListData();
    	
    	when(courseRepository.findByRegistrant_email(any(String.class))).thenReturn(courseList);
    	
    	List<CourseDto> CourseDto = courseService.getCourseByUser("admin");
    	
    	assertThat(CourseDto, notNullValue());
    	
    	verify(courseRepository, times(1)).findByRegistrant_email(any(String.class));
    }
    
    @Test
	public void addCourse() {
    	CourseEntity courseEntity = getCourseData();
    	
    	CourseDto courseDto = CourseDto.builder()
				.id(courseEntity.getId())
				.registrant(courseEntity.getRegistrant().getEmail())
				.title(courseEntity.getTitle())
		        .content(courseEntity.getContent())
		        .divclsNo(courseEntity.getDivclsNo())
		        .category(courseEntity.getCategory().getId())
		        .categoryName(courseEntity.getCategory().getName())
		        .maxNum(courseEntity.getMaxNum())
		        .curNum(courseEntity.getCurNum())
		        .node(jsonToList(courseEntity.getNode()))
		        .edge(jsonToList(courseEntity.getEdge()))
		        .createdDate(courseEntity.getCreatedDate())
		        .build();
    	
    	UserEntity user =  UserEntity.builder()
				.email("cys779988@naver.com")
				.name("채영수")
				.role(Role.GUEST)
				.password("")
				.build();
    	
    	String node = "[{\"data\":{\"id\":\"Main\",\"name\":\"Main\",\"content\":\"Main\",\"shape\":\"round-hexagon\",\"width\":80,\"height\":40,\"font\":7,\"color\":\"#D0C1B2\"},\"renderedPosition\":{\"x\":-6.666666666666669,\"y\":37.333333333333336}}]";
    	String edge = "[]";
    	
    	when(courseRepository.save(any(CourseEntity.class))).thenReturn(new CourseEntity(1L, user, "테스트제목", "테스트글", CategoryEntity.builder().id(1L).build(), 2, 20, 3, node, edge));

    	Long id = courseService.addCourse(courseDto);
    	
    	assertThat(courseDto.getId(), is(id));
    	
    	verify(courseRepository, times(1)).save(any(CourseEntity.class));
	}
}
 