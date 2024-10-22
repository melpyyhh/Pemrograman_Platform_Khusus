package com.melpy.perpus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.melpy.perpus.entity.BookLoan;

@RepositoryRestResource(collectionResourceRel = "loans", path = "loans")
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

    List<BookLoan> findByMemberId(@Param("member_id") Long memberId);

    List<BookLoan> findByBookId(@Param("book_id") Long bookId);
}
