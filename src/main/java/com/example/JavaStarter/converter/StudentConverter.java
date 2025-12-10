package com.example.JavaStarter.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class StudentConverter implements AttributeConverter<List<String>,String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        String res =  strings==null?"":String.join(",",strings);
        return res;
    }

    @Override
    public List<String> convertToEntityAttribute(String data) {
        return (data==null || data.isEmpty())?List.of():List.of(data.split(","));
    }
}
