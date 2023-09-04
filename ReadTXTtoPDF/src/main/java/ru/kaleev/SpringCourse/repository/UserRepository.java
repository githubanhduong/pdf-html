package ru.kaleev.SpringCourse.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ru.kaleev.SpringCourse.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer>{
	User findUserByUsernameAndPassword(String username,String password);
	Page<User> findAllByAuthor(String author, Pageable pageable);
	Page<User> findByAuthorContaining(String author, Pageable pageable);
	List<User> findByAuthorContaining(String author);
	long countByAuthor(String author);
}
