package com.nhnacademy.miniproject.common.converter;

import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.model.Role;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;

public class CsvCreateMemberRequestHttpMessageConverter extends AbstractHttpMessageConverter<CreateMemberRequest> {

    public CsvCreateMemberRequestHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return CreateMemberRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected CreateMemberRequest readInternal(Class<? extends CreateMemberRequest> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputMessage.getBody()))) {
            String settingLine = reader.readLine();
            String data = reader.readLine();
            String[] tokens = data.split(",");

            if(tokens.length<5){
                throw new RuntimeException();
            }

            CreateMemberRequest newMember = new CreateMemberRequest(
                    tokens[0].trim(),
                    tokens[1].trim(),
                    tokens[2].trim(),
                    Integer.parseInt(tokens[3].trim()),
                    Role.valueOf(tokens[4].trim())
            );

            return newMember;
        }
    }

    @Override
    protected void writeInternal(CreateMemberRequest member, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(outputMessage.getBody()))){
            writer.write("id,  " + "name,   " + "password,  " + "age,  " + "role," + "\n");
            writer.write(member.getId() + ", "+member.getName() + ", "+member.getPassword()+", "+member.getAge()+ ", "+member.getRole());
            writer.flush();
        }
    }
}
