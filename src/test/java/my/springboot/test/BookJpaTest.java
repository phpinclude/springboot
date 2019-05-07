package my.springboot.test;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import my.springboot.domain.Book;
import my.springboot.repository.BookRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection=EmbeddedDatabaseConnection.H2)
public class BookJpaTest {
	
	private final static String BOOT_TEST_TITLE = "Spring Boot Test Book";
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private BookRepository bookRepository;
	
//	@Test
//	public void bookSaveTest() {
//		Book book = Book.builder().title(BOOT_TEST_TITLE).publishedAt(LocalDateTime.now()).build();
//		
//		testEntityManager.persist(book);
//		
//		Assert.assertThat(bookRepository.getOne(book.getIdx()), Matchers.is(book));
//	}
//	
//	@Test
//	public void bookListSaveSearchTest() {
//		Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
//		
//		testEntityManager.persist(book1);
//		
//		Book book2 = Book.builder().title(BOOT_TEST_TITLE + "2").publishedAt(LocalDateTime.now()).build();
//		
//		testEntityManager.persist(book2);
//		
//		Book book3 = Book.builder().title(BOOT_TEST_TITLE + "3").publishedAt(LocalDateTime.now()).build();
//		
//		testEntityManager.persist(book3);
//		
//		List<Book> bookList = bookRepository.findAll();
//		
//		for (Book book : bookList) System.out.println(book);
//		
//		Assert.assertThat(bookList, Matchers.hasSize(3));
//		Assert.assertThat(bookList, Matchers.contains(book1,book2,book3));
//		
//	}
//	
//	
//	@Test
//	public void bookListSaveDeleteTest() {
//		Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
//		
//		testEntityManager.persist(book1);
//		
//		Book book2 = Book.builder().title(BOOT_TEST_TITLE + "2").publishedAt(LocalDateTime.now()).build();
//		
//		testEntityManager.persist(book2);
//		
//		List<Book> bookList = bookRepository.findAll();
//		Assert.assertThat(bookList, Matchers.contains(book1,book2));
//		
//		Assert.assertThat(bookList, Matchers.hasSize(2));
//		bookRepository.delete(book1);
//		
//		bookList = bookRepository.findAll();
//		Assert.assertThat(bookList, Matchers.hasSize(1));
//		
//		
//		bookRepository.deleteById(book2.getIdx());
//		
//		Assert.assertThat(bookRepository.findAll(), IsEmptyCollection.empty());
//	}
}
