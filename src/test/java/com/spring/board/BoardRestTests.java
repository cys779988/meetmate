package com.spring.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.model.BoardDto;
import com.spring.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Slf4j
class BoardRestTests {

	@Autowired
	MockMvc mockMvc;
	
	static BoardRepository boardRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void insertBoard() {
		log.info("----------------------------------@beforeAll");
		/*
		 * for (int i = 0; i < 5; i++) { boardRepository.save(BoardEntity.builder()
		 * .title("제목" + i) .content("내용" + i) .writer("작성자" + i) .build()); }
		 */
	}
	
	@Test
	public void addBoard() throws Exception{
		BoardDto boardDto = BoardDto.builder()
				.title("추가된 제목")
				.content("추가된 내용")
				.writer("추가된 작성자")
				.build();

		mockMvc.perform(post("/api/board/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		this.getBoard();
	}
	
	@Test
	public void editBoard() throws Exception{
		BoardDto boardDto = BoardDto.builder()
				.title("수정된 제목")
				.content("수정된 내용")
				.writer("수정된 작성자")
				.build();
		
		mockMvc.perform(put("/api/board/4")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardDto)))
				.andDo(print())
				.andExpect(status().isOk());
		this.getBoard();
	}

	@Test
	private void getBoard() throws Exception{
		mockMvc.perform(get("/api/board/all/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Disabled
	public void deleteBoard() throws Exception{
		mockMvc.perform(delete("/api/board/103")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		this.getBoard();
		assertThat("asd").isEqualTo("asd");
	}

}
