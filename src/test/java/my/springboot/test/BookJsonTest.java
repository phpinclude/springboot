package my.springboot.test;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import my.springboot.domain.Book;

@RunWith(SpringRunner.class)
@JsonTest
public class BookJsonTest {
	
	@Autowired
	private JacksonTester<Book> json;
	
	@Test
	public void jsonTest() throws IOException {
		Book book = Book.builder().title("테스트").build();
		
		String content = "{\"title\":\"테스트\"}";
		
		Assertions.assertThat(this.json.parseObject(content).getTitle()).isEqualTo(book.getTitle());
		
		Assertions.assertThat(this.json.parseObject(content).getPublishedAt()).isNull();
		Assertions.assertThat(this.json.write(book)).isEqualToJson("/test.json");
		Assertions.assertThat(this.json.write(book)).hasJsonPathStringValue("title");
		Assertions.assertThat(this.json.write(book)).extractingJsonPathStringValue("title").isEqualTo("테스트");
	}
}
