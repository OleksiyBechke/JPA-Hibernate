package org.example.app.DAO.impl;

import org.example.app.config.HibernateConfig;
import org.example.app.entity.Student;
import org.example.app.DAO.GenericDAO;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.List;

public class StudentDAO implements GenericDAO<Student, Long> {

    @Override
    public void save(Student student) {
        if(findByEmail(student.getEmail()) == null) {
            Transaction transaction = null;
            try (Session session = HibernateConfig.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.persist(student);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("Помилка збереження студента: " + e.getMessage());
            }
        }
    }

    @Override
    public Student findById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);

            if (student != null) {
                Hibernate.initialize(student.getHomeworks());
            }

            transaction.commit();
            return student;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Помилка пошуку студента за id: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Student findByEmail(String email) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = " FROM Student s WHERE s.email = :email";
            org.hibernate.query.Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("email", email);
            Student student = query.uniqueResult();

            if (student != null) {
                Hibernate.initialize(student.getHomeworks());
            }

            transaction.commit();
            return student;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Помилка пошуку студента за email: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Student> findAll() {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Student> list = session.createQuery("FROM Student", Student.class).list();
            for (Student student : list) {
                Hibernate.initialize(student.getHomeworks());
            }
            transaction.commit();
            return list;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Помилка пошуку студента: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Student update(Student student) {
        if (findById(student.getId()) == null) {
            return null;
        } else {
            Transaction transaction = null;
            try (Session session = HibernateConfig.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                String hql = "UPDATE Student " +
                        "SET firstName = :firstName, lastName = :lastName, " +
                        "email = :email WHERE id = :id";
                MutationQuery query = session.createMutationQuery(hql);
                query.setParameter("firstName", student.getFirstName());
                query.setParameter("lastName", student.getLastName());
                query.setParameter("email", student.getEmail());
                query.setParameter("id", student.getId());
                int result = query.executeUpdate();
                transaction.commit();
                if (result > 0) {
                    return student;
                } else {
                    return null;
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("Помилка оновлення студента: " + e.getMessage());
                return null;
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id) == null) {
            return false;
        } else {
            Transaction transaction = null;
            try (Session session = HibernateConfig.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                String hql = "DELETE FROM Student WHERE id = :id";
                MutationQuery query = session.createMutationQuery(hql);
                query.setParameter("id", id);
                int result = query.executeUpdate();
                transaction.commit();
                return result > 0;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("Помилка видалення студента: " + e.getMessage());
                return false;
            }
        }
    }
}
