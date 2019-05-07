package my.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import my.springboot.domain.Book;
import my.springboot.service.BookRestService;

@Controller
public class BookRestController {
	
	@Autowired
	private BookRestService bookRestService;
	
	@GetMapping(path="/rest/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public Book getRestBook() {
		return bookRestService.getRestBook();
	}
}
