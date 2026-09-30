package com.darshdeep.studentapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void getStudentByIdShouldReturn200() throws Exception {

        Student student = new Student(
                1,
                "Darshdeep Singh",
                "Computer Science",
                3.76
        );

        when(studentService.getStudentById(1))
                .thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Darshdeep Singh"))
                .andExpect(jsonPath("$.major").value("Computer Science"))
                .andExpect(jsonPath("$.gpa").value(3.76));
    }

    @Test
    void getStudentByIdShouldReturn404() throws Exception {

        when(studentService.getStudentById(999))
                .thenReturn(null);

        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addStudentShouldReturn201() throws Exception {

        Student student = new Student(
                2,
                "Alex Johnson",
                "Information Systems",
                3.50
        );

        when(studentService.addStudent(any(Student.class)))
                .thenReturn(student);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id": 2,
                          "name": "Alex Johnson",
                          "major": "Information Systems",
                          "gpa": 3.50
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Alex Johnson"))
                .andExpect(jsonPath("$.major").value("Information Systems"))
                .andExpect(jsonPath("$.gpa").value(3.50));
    }

    @Test
    void addStudentWithInvalidGpaShouldReturn400() throws Exception {

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id": 3,
                          "name": "Test Student",
                          "major": "Computer Science",
                          "gpa": 5.0
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.gpa")
                        .value("GPA must not be greater than 4.0"));
    }

    @Test
    void addStudentWithBlankNameShouldReturn400() throws Exception {

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id": 3,
                          "name": "",
                          "major": "Computer Science",
                          "gpa": 3.50
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Name is required"));
    }

    @Test
    void updateStudentShouldReturn200() throws Exception {

        Student updatedStudent = new Student(
                1,
                "Darshdeep Singh",
                "Artificial Intelligence",
                3.90
        );

        when(studentService.updateStudent(
                org.mockito.ArgumentMatchers.eq(1),
                any(Student.class)))
                .thenReturn(updatedStudent);

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Darshdeep Singh",
                          "major": "Artificial Intelligence",
                          "gpa": 3.90
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Darshdeep Singh"))
                .andExpect(jsonPath("$.major").value("Artificial Intelligence"))
                .andExpect(jsonPath("$.gpa").value(3.90));
    }

    @Test
    void updateStudentShouldReturn404() throws Exception {

        when(studentService.updateStudent(
                org.mockito.ArgumentMatchers.eq(999),
                any(Student.class)))
                .thenReturn(null);

        mockMvc.perform(put("/students/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Test Student",
                          "major": "Computer Science",
                          "gpa": 3.00
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudentShouldReturn204() throws Exception {

        Student student = new Student(
                1,
                "Darshdeep Singh",
                "Computer Science",
                3.76
        );

        when(studentService.getStudentById(1))
                .thenReturn(student);

        when(studentService.deleteStudent(1))
                .thenReturn("Student with ID 1 deleted successfully.");

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudentShouldReturn404() throws Exception {

        when(studentService.getStudentById(999))
                .thenReturn(null);

        mockMvc.perform(delete("/students/999"))
                .andExpect(status().isNotFound());
    }
}