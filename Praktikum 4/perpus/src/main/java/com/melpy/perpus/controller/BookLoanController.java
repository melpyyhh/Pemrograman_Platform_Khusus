package com.melpy.perpus.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melpy.perpus.entity.Book;
import com.melpy.perpus.entity.BookLoan;
import com.melpy.perpus.entity.Member;
import com.melpy.perpus.repository.BookLoanRepository;
import com.melpy.perpus.repository.BookRepository;
import com.melpy.perpus.repository.MemberRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/loans")
public class BookLoanController {
    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/pinjam")
    public ResponseEntity<Object> pinjamBuku(@RequestBody BookLoan bookLoanRequest) {

        Optional<Member> member = memberRepository.findById(bookLoanRequest.getMember().getId());
        if (member.isEmpty()) {
            return ResponseEntity.badRequest().body("Member dengan ID tersebut tidak ditemukan.");
        }

        // Validasi keberadaan buku di database
        Optional<Book> book = bookRepository.findById(bookLoanRequest.getBook().getId());
        if (book.isEmpty()) {
            return ResponseEntity.badRequest().body("Buku dengan ID tersebut tidak ditemukan.");
        }

        List<BookLoan> existingLoans = bookLoanRepository.findByBookId(bookLoanRequest.getBook().getId());
        for (BookLoan existingLoan : existingLoans) {
            if ("DIPINJAM".equals(existingLoan.getStatus())) {
                return ResponseEntity.badRequest().body("Buku ini sudah dipinjam.");
            }
        }

        bookLoanRequest.setTanggalPinjam(LocalDate.now());
        ;
        bookLoanRequest.setStatus("DIPINJAM");
        bookLoanRequest.setTelatHari(0);

        BookLoan newLoan = bookLoanRepository.save(bookLoanRequest);
        newLoan.setMember(member.get());
        newLoan.setBook(book.get());
        return ResponseEntity.ok(newLoan);
    }

    @PutMapping("{loanid}/kembali")
    public ResponseEntity<BookLoan> kembaliBuku(@PathVariable Long loanid) {
        BookLoan bookLoan = bookLoanRepository.findById(loanid)
                .orElseThrow(() -> new RuntimeException("Peminjaman tidak ditemukan"));
        bookLoan.setTanggalKembali(LocalDate.now());
        bookLoan.setStatus("DIKEMBALIKAN");

        if (bookLoan.getTanggalKembali().isAfter(bookLoan.getTanggalPinjam().plusDays(7))) {
            bookLoan.setTelatHari(
                    (int) bookLoan.getTanggalPinjam().plusDays(7).until(bookLoan.getTanggalKembali()).getDays());
        }

        BookLoan updatedLoan = bookLoanRepository.save(bookLoan);
        return ResponseEntity.ok(updatedLoan);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookLoan>> getLoansByMemberId(@PathVariable Long memberId) {
        List<BookLoan> loans = bookLoanRepository.findByMemberId(memberId);
        return ResponseEntity.ok(loans);
    }

}
