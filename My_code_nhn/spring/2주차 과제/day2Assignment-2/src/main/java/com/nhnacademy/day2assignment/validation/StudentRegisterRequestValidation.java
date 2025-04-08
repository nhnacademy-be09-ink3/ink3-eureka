package com.nhnacademy.day2assignment.validation;

import com.nhnacademy.day2assignment.domain.StudentRegisterRequest;
import com.nhnacademy.day2assignment.domain.StudentRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StudentRegisterRequestValidation implements Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentRegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRegisterRequest request = (StudentRegisterRequest) target;

        String name = request.getName().trim();
        if(name.length()==0){
            errors.rejectValue("name","","name is empty");
        }

        int grade = request.getGrade();
        if(grade<0 || grade>100){
            errors.rejectValue("grade","","grade is range of 0~100");
        }

        String evaluation = request.getEvaluation().trim();
        if(evaluation.length()==0 || evaluation.length()>200){
            errors.rejectValue("evaluation","","evaluation is range of 0~200");
        }

        String email = request.getEmail();
        if(!isValidEmail(email)){
            errors.rejectValue("email","","email has wrong format");
        }
    }


    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches(EMAIL_REGEX);
    }

}
