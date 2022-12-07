package com.spring.code;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.spring.code.model.CodeRequest;
import com.spring.code.model.CodeType;
import com.spring.code.model.CommonCodeEntity;
import com.spring.code.repository.CommonCodeRepository;
import com.spring.code.service.CommonCodeService;
import com.spring.common.model.GridForm;
import com.spring.common.model.GridResponse;
import com.spring.common.util.AppUtil;
import com.spring.common.util.GridUtil;

@ExtendWith(MockitoExtension.class)
class CodeServiceTest {

	@InjectMocks
	private CommonCodeService commonCodeService;
	
    @Mock
    private CommonCodeRepository commonCodeRepository;	

    private List<CommonCodeEntity> getCodeListData() {
    	
    	List<CommonCodeEntity> boardList = Arrays.asList(
			CommonCodeEntity.builder().id(1L).name("sample1").type(CodeType.COURSE_CATEGORY).build()
			, CommonCodeEntity.builder().id(2L).name("sample2").type(CodeType.COURSE_CATEGORY).build()
			, CommonCodeEntity.builder().id(3L).name("sample3").type(CodeType.COURSE_CATEGORY).build()
    	);
    	
    	return boardList;
    }
    
    @Test
    public void getCodesGridForm() {
    	Pageable page = PageRequest.of(0, 10);
    	Page<CommonCodeEntity> testData = new PageImpl<CommonCodeEntity>(getCodeListData());
    	
    	when(commonCodeRepository.findByType(page, CodeType.COURSE_CATEGORY)).thenReturn(testData);
    	
    	try(MockedStatic<GridUtil> gridUtil = Mockito.mockStatic(GridUtil.class)) {
    		when(GridUtil.of(anyInt(), anyLong(), anyList())).thenReturn(new GridForm(true, new GridResponse()));
    		
    		GridForm boardsGridForm = commonCodeService.getCodesGridForm(page, CodeType.COURSE_CATEGORY);
    	
    		assertThat(boardsGridForm.getData(), notNullValue());
    	}
    }
    
    @Test
    public void getCodes() {
    	
    	when(commonCodeRepository.findByType(CodeType.COURSE_CATEGORY)).thenReturn(getCodeListData());
    	
    	Map<String, String> codes = commonCodeService.getCodes(CodeType.COURSE_CATEGORY);
    	
    	assertThat(codes, notNullValue());
    	
    	verify(commonCodeRepository, times(1)).findByType(any());
    }
    
    @SuppressWarnings("unchecked")
	@Test
	public void addCodes() {
    	List<CommonCodeEntity> codeEntityList = getCodeListData();
    	
    	when(commonCodeRepository.saveAll(any(List.class))).thenReturn(codeEntityList);

    	try(MockedStatic<AppUtil> appUtil = Mockito.mockStatic(AppUtil.class)) {
    		when(AppUtil.getUser()).thenReturn("admin");
    		List<CommonCodeEntity> codeList = commonCodeService.addCodes(new CodeRequest());
    		assertThat(codeList.get(0).getId(), is(1L));
    	}
    	
    	verify(commonCodeRepository, times(1)).saveAll(anyList());
	}
}
 