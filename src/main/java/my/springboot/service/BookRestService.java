package my.springboot.service;

import org.springframework.stereotype.Service;

import my.springboot.domain.Book;

@Service
public class BookRestService {
	
	public Book getRestBook() {
		return Book.builder().title("테스트").build();
	}

}
