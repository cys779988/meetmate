package com.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.board.model.BoardEntity;
import com.spring.board.repository.BoardRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DaoUnitTest {

	@Autowired private BoardRepository boardRepository;

    @Test
    public void findAllTest() {
        List<BoardEntity> boardList = boardRepository.findAll();
        assertThat(boardList)
		        .isNotEmpty()
		        .hasSize(3);
    }

}