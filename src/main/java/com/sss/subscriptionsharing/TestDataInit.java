package com.sss.subscriptionsharing;

import java.util.List;

import com.sss.subscriptionsharing.domain.Board;
import com.sss.subscriptionsharing.domain.Category;
import com.sss.subscriptionsharing.repository.BoardRepository;
import com.sss.subscriptionsharing.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    @PostConstruct
	public void dataInItCheck() {
		List<Category> categories = categoryRepository.findAll();

		for (Category category : categories) {
			log.info("categoryId={}, categoryName={}",category.getId(), category.getName());
		}
	}

    // @PostConstruct
    // public void categoryInit() {
	//
    //     Board watcha = Board.create("왓챠");
    //     boardRepository.save(watcha);
	//
    //     categoryRepository.save(Category.create("자유 게시판", watcha));
    //     categoryRepository.save(Category.create("구인 게시판", watcha));
    //     categoryRepository.save(Category.create("게시판 추천 게시판", watcha));
	//
    //     Board wavve = Board.create("웨이브");
    //     boardRepository.save(wavve);
	//
    //     categoryRepository.save(Category.create("자유 게시판", wavve));
    //     categoryRepository.save(Category.create("구인 게시판", wavve));
    //     categoryRepository.save(Category.create("게시판 추천 게시판", wavve));
	//
    //     Board spotify = Board.create("스포티파이");
    //     boardRepository.save(spotify);
	//
    //     categoryRepository.save(Category.create("자유 게시판", spotify));
    //     categoryRepository.save(Category.create("구인 게시판", spotify));
    //     categoryRepository.save(Category.create("게시판 추천 게시판", spotify));
	//
    //     Board none = Board.create("none");
    //     boardRepository.save(none);
    //     categoryRepository.save(Category.create("OTT 추천 게시판", none));
    // }
}
