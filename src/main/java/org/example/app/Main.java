package org.example.app;

import org.example.app.entity.Student;
import org.example.app.entity.Homework;
import org.example.app.DAO.impl.StudentDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        Homework homework = new Homework();
        homework.setDescription("Math Homework");
        homework.setDeadline(LocalDate.of(2024, 12, 1));
        homework.setMark(90);

        student.addHomework(homework);

        studentDAO.save(student);
        System.out.println("Saved student : " + student);

        Student studentFoundByEmail = studentDAO.findByEmail("john.doe@example.com");
        System.out.println("Student found by email: " + studentFoundByEmail);

        studentFoundByEmail.setEmail("john.newemail@example.com");
        studentDAO.update(studentFoundByEmail);
        System.out.println("Updated student: " + studentFoundByEmail);

        boolean isDeleted = studentDAO.deleteById(studentFoundByEmail.getId());
        System.out.println("Deleted student : " + isDeleted);

        Random rand = new Random();

        for (int i = 1; i <= 3; i++) {
            Student stud = new Student();
            stud.setFirstName("student_f_n_" + i);
            stud.setLastName("student_s_n_" + i);
            stud.setEmail("student" + i + "@example.com");

            Homework hw = new Homework();
            hw.setDescription("Homework" + i);
            hw.setDeadline(LocalDate.of(2024, 12, rand.nextInt(30) + 1));
            hw.setMark(rand.nextInt(101));

            stud.addHomework(hw);

            studentDAO.save(stud);

            System.out.println("Saved student : " + stud);
        }

        List<Student> students = studentDAO.findAll();
        for (Student s : students) {
            System.out.println("Student : " + s);
        }
    }
}
