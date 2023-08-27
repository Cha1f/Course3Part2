package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.exceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }


    public Collection<Faculty> findByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }

    public Collection<Faculty> getAllByNameOrColor(String name, String color) {
        return facultyRepository.findAllByColorLikeIgnoreCaseOrNameLikeIgnoreCase(color, name);
    }

    public Faculty getByStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(StudentNotFoundException::new);
    }
}
