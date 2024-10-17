package com.melpy.web.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.melpy.web.services.MahasiswaRepository;

import jakarta.validation.Valid;

import com.melpy.web.models.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {

    @Autowired
    private MahasiswaRepository repo;

    @GetMapping({ "", "/" })
    public String showMahasiswa(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Mahasiswa> mahasiswa;
        MahasiswaDto mahasiswaDto = new MahasiswaDto();
        if (keyword != null) {
            mahasiswa = repo.cariByNimOrNama(keyword);
        } else {
            mahasiswa = repo.findAll();
        }
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("keyword", keyword);
        model.addAttribute("mahasiswaDto", mahasiswaDto);
        return "mahasiswa/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        MahasiswaDto mahasiswaDto = new MahasiswaDto();
        model.addAttribute("mahasiswaDto", mahasiswaDto);
        return "mahasiswa/createMahasiswa";
    }

    @PostMapping("/create")
    public String createMahasiswa(@Valid @ModelAttribute MahasiswaDto mahasiswaDto, BindingResult result) {

        if (result.hasErrors()) {
            return "mahasiswa/createMahasiswa";
        }

        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNama(mahasiswaDto.getNama());
        mahasiswa.setJurusan(mahasiswaDto.getJurusan());
        mahasiswa.setNim(mahasiswaDto.getNim());
        mahasiswa.setTanggalLahir(mahasiswaDto.getTanggalLahir());
        repo.save(mahasiswa);

        return "redirect:/mahasiswa";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam(defaultValue = "") String nim) {

        try {
            Mahasiswa mahasiswa = repo.findByNimEquals(nim);
            model.addAttribute("mahasiswa", mahasiswa);

            MahasiswaDto mahasiswaDto = new MahasiswaDto();
            mahasiswaDto.setNama(mahasiswa.getNama());
            mahasiswaDto.setJurusan(mahasiswa.getJurusan());
            mahasiswaDto.setNim(mahasiswa.getNim());
            mahasiswaDto.setTanggalLahir(mahasiswa.getTanggalLahir());

            model.addAttribute("mahasiswaDto", mahasiswaDto);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "redirect:/mahasiswa";
        }
        return "mahasiswa/editMahasiswa";
    }

    @PostMapping("/edit")
    public String updateMahasiswa(Model model, @RequestParam String nim,
            @Valid @ModelAttribute MahasiswaDto mahasiswaDto, BindingResult result) {

        try {
            Mahasiswa mahasiswa = repo.findByNimEquals(nim);
            model.addAttribute("mahasiswa", mahasiswa);

            if (result.hasErrors()) {
                return "mahasiswa/editMahasiswa";
            }

            mahasiswa.setNama(mahasiswaDto.getNama());
            mahasiswa.setJurusan(mahasiswaDto.getJurusan());
            mahasiswa.setTanggalLahir(mahasiswaDto.getTanggalLahir());
            repo.save(mahasiswa);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "redirect:/mahasiswa";
        }

        return "redirect:/mahasiswa";
    }

    @GetMapping("/delete")
    public String getMethodName(@RequestParam String nim) {
        try {
            Mahasiswa mahasiswa = repo.findByNimEquals(nim);
            repo.delete(mahasiswa);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "redirect:/mahasiswa";
        }
        return "redirect:/mahasiswa";
    }

    @GetMapping("/detail")
    public String showDetailPage(Model model, @RequestParam(defaultValue = "") String nim) {
        try {
            Mahasiswa mahasiswa = repo.findByNimEquals(nim);
            model.addAttribute("mahasiswa", mahasiswa);

            MahasiswaDto mahasiswaDto = new MahasiswaDto();
            mahasiswaDto.setNama(mahasiswa.getNama());
            mahasiswaDto.setJurusan(mahasiswa.getJurusan());
            mahasiswaDto.setNim(mahasiswa.getNim());
            mahasiswaDto.setTanggalLahir(mahasiswa.getTanggalLahir());

            model.addAttribute("mahasiswaDto", mahasiswaDto);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "redirect:/mahasiswa";
        }
        return "mahasiswa/detailMahasiswa";
    }

}
