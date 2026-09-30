package com.darshdeep.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);
    }

    @Test
    void getStudentByIdShouldReturnStudent() {

        Student student = new Student(
                1,
                "Darshdeep Singh",
                "Computer Science",
                3.76
        );

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Darshdeep Singh", result.getName());
        assertEquals("Computer Science", result.getMajor());
        assertEquals(3.76, result.getGpa(), 0.001);
    }

    @Test
    void getStudentByIdShouldReturnNullWhenStudentDoesNotExist() {

        when(studentRepository.findById(999))
                .thenReturn(Optional.empty());

        Student result = studentService.getStudentById(999);

        assertNull(result);
    }

    @Test
    void addStudentShouldSaveAndReturnStudent() {

        Student student = new Student(
                2,
                "Alex Johnson",
                "Information Systems",
                3.50
        );

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.addStudent(student);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Alex Johnson", result.getName());
        assertEquals("Information Systems", result.getMajor());
        assertEquals(3.50, result.getGpa(), 0.001);
    }

    @Test
    void updateStudentShouldUpdateAndReturnStudent() {

        Student existingStudent = new Student(
                1,
                "Darshdeep Singh",
                "Computer Science",
                3.76
        );

        Student updatedStudent = new Student(
                1,
                "Darshdeep Singh",
                "Artificial Intelligence",
                3.90
        );

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(existingStudent));

        when(studentRepository.save(existingStudent))
                .thenReturn(existingStudent);

        Student result = studentService.updateStudent(1, updatedStudent);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Darshdeep Singh", result.getName());
        assertEquals("Artificial Intelligence", result.getMajor());
        assertEquals(3.90, result.getGpa(), 0.001);
    }

    @Test
    void deleteStudentShouldDeleteExistingStudent() {

        when(studentRepository.existsById(2))
                .thenReturn(true);

        String result = studentService.deleteStudent(2);

        verify(studentRepository).deleteById(2);

        assertEquals(
                "Student with ID 2 deleted successfully.",
                result
        );
    }
}