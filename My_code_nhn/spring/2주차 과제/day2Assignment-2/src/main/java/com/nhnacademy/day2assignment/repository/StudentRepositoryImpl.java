package com.nhnacademy.day2assignment.repository;

import com.nhnacademy.day2assignment.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class StudentRepositoryImpl implements StudentRepository{

    Map<String, Object> studentMap;

    public StudentRepositoryImpl(){
        studentMap = new HashMap<>();
        studentMap.put("kwon", new Student("kwon", "123", "권용민", "alskdjfhg005@naver.com", 100, "great"));
    }

    public Map<String, Object> getStudentRepository(){
        return studentMap;
    }


    @Override
    public Student getStudent(String id) {
        return (Student) studentMap.get(id);
    }

    @Override
    public void registerStudent(Student student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public void updateStudent(Student updateStudent) {
        studentMap.put(updateStudent.getId(), updateStudent);
    }

    @Override
    public boolean doLogin(String id, String password) {

        if(!studentMap.containsKey(id)){
            return false;
        }
        Student student = (Student) studentMap.get(id);
        if(!password.equals(student.getPassword())){
            return false;
        }

        return true;
    }
}
