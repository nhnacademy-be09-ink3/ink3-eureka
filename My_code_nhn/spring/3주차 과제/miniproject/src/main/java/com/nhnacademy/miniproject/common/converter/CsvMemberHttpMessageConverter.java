package com.nhnacademy.miniproject.common.converter;

import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.domain.member.model.Role;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;

public class CsvMemberHttpMessageConverter extends AbstractHttpMessageConverter<Member> {

    public CsvMemberHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    protected Member readInternal(Class<? extends Member> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputMessage.getBody()))) {
            String data = reader.readLine();
            String[] tokens = data.split(",");

            if(tokens.length<5){
                throw new RuntimeException();
            }

            Member newMember = new Member(
                    tokens[0],
                    tokens[1],
                    tokens[2],
                    Integer.parseInt(tokens[3]),
                    Role.valueOf(tokens[4])
            );

            return newMember;
        }
    }

    @Override
    protected void writeInternal(Member member, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(outputMessage.getBody()))){
            writer.write("id,  " + "name,   " + "password,  " + "age,  " + "role," + "\n");
            writer.write(member.getId() + ", "+member.getName() + ", "+member.getPassword()+", "+member.getAge()+ ", "+member.getRole());
            writer.flush();
        }
    }
}
