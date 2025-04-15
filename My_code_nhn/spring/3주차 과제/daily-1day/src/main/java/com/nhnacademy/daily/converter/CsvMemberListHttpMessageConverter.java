package com.nhnacademy.daily.converter;

import com.nhnacademy.daily.model.member.Member;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class CsvMemberListHttpMessageConverter extends AbstractHttpMessageConverter<List<Member>> {

    public CsvMemberListHttpMessageConverter() {
        super(new MediaType("text","csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @Override
    protected List<Member> readInternal(Class<? extends List<Member>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(List<Member> memberList, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(MediaType.valueOf("text/csv; charset=UTF-8"));
        try (Writer writer = new OutputStreamWriter(outputMessage.getBody())) {
            for(Member member : memberList){
                writer.write("id, name, class, locale ");
                writer.write(member.getId() + " " + member.getName() + " " + member.getClazz() + " " + member.getLocale()+"\n");
            }
        }
    }
}
