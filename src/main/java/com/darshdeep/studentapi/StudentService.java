package com.darshdeep.studentapi;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all students
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // Get one student by ID
    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Add a new student
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // Update an existing student
    public Student updateStudent(int id, Student updatedStudent) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student != null) {
            student.setName(updatedStudent.getName());
            student.setMajor(updatedStudent.getMajor());
            student.setGpa(updatedStudent.getGpa());

            return studentRepository.save(student);
        }

        return null;
    }

    // Delete a student
    public String deleteStudent(int id) {

        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);

            return "Student with ID "
                    + id
                    + " deleted successfully.";
        }

        return "Student with ID "
                + id
                + " not found.";
    }
}