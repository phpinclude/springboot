package my.springboot.test;

import java.time.LocalDateTime;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import my.springboot.controller.BookController;
import my.springboot.domain.Book;
import my.springboot.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BookContollerTest {
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private BookService bookService;
    
    @Test
    public void Book_MVC_test() throws Exception{
        Book book = new Book("Spring boot 책", LocalDateTime.now());
        
        BDDMockito.given(bookService.getBookList()).willReturn(Collections.singletonList(book));
        
        mvc.perform(MockMvcRequestBuilders.get("/books"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("book"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("bookList"))
            .andExpect(MockMvcResultMatchers.model().attribute("bookList", Matchers.contains(book)));
        
        
        
    }
    
    @Test
    public void Book_MVC_test2() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/test"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
