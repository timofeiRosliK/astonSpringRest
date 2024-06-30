package org.example.web;

import org.example.dto.DoctorCreateDto;
import org.example.dto.DoctorResponseDto;
import org.example.dto.DoctorUpdateDto;
import org.example.mapper.DoctorMapper;
import org.example.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorController(DoctorService doctorService, DoctorMapper doctorMapper) {
        this.doctorService = doctorService;
        this.doctorMapper = doctorMapper;
    }

    @PostMapping
    public DoctorResponseDto saveDoctorData(@RequestBody DoctorCreateDto doctorCreateDto) {
        return doctorService.saveDoctor(doctorMapper.toDoctor(doctorCreateDto));
    }

    @GetMapping(value = "/{id}")
    public DoctorResponseDto getDoctorById(@PathVariable("id") int id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping(value = "/{id}")
    public DoctorResponseDto updateDoctorData(@PathVariable("id") int id, @RequestBody DoctorUpdateDto doctorUpdateDto) {
        return doctorService.updateDoctorById(id, doctorMapper.toDoctor(doctorUpdateDto));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDoctorData(@PathVariable("id") int id) {
        doctorService.removeDoctor(id);
    }

}
