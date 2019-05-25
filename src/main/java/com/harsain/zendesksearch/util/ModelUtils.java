package com.harsain.zendesksearch.util;

import com.harsain.zendesksearch.exception.InvalidInputException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelUtils {

    public List<String> getPropertiesForClass(Class clazz) throws InvalidInputException {
        if (clazz != null) {
            return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        } else {
            throw new InvalidInputException("Invalid input, unable to get properties");
        }
    }
}
