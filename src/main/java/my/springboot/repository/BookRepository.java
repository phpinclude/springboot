package my.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.springboot.domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
