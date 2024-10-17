package com.melpy.web.services;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.melpy.web.models.Mahasiswa;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Integer> {
    Mahasiswa findByNimEquals(String nim);

    @Query("SELECT m FROM Mahasiswa m WHERE m.nim LIKE %:keyword% OR m.nama LIKE %:keyword%")
    public List<Mahasiswa> cariByNimOrNama(@Param("keyword") String keyword);
}
