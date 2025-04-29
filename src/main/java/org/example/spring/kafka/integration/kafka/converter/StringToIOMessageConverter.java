package org.example.spring.kafka.integration.kafka.converter;

import jakarta.annotation.Nonnull;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.spring.kafka.integration.kafka.model.IOMessage;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class StringToIOMessageConverter implements Converter<String, IOMessage> {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public IOMessage convert(@Nonnull String source) {
        return objectMapper.readValue(source, objectMapper.constructType(new TypeReference<IOMessage>() {}));
    }
}
