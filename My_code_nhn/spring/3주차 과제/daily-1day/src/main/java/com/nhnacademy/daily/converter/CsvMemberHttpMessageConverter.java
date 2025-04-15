package com.nhnacademy.daily.converter;

import com.nhnacademy.daily.model.member.Locale;
import com.nhnacademy.daily.model.member.Member;
import com.nhnacademy.daily.model.member.MemberRequest;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class CsvMemberHttpMessageConverter extends AbstractHttpMessageConverter<MemberRequest> {

    public CsvMemberHttpMessageConverter() {
        super(new MediaType("text","csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return MemberRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected MemberRequest readInternal(Class<? extends MemberRequest> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputMessage.getBody()))) {
            String data = bufferedReader.readLine();

            if(Objects.isNull(data) || data.isEmpty()){
                throw new IllegalArgumentException();
            }

            String[] tokens = data.split(",");
            if(tokens.length<4){
                throw new IllegalArgumentException();
            }

            MemberRequest newMember = new MemberRequest(
                    tokens[0].trim(),
                    tokens[1].trim(),
                    Integer.parseInt(tokens[2].trim()),
                    tokens[3].trim()
            );
            return newMember;
        }
    }

    @Override
    protected void writeInternal(MemberRequest memberRequest, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }

}
