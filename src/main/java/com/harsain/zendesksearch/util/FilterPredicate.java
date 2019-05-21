package com.harsain.zendesksearch.util;

import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class FilterPredicate {
    public <T> Predicate<T> getGenericPredicate(String key, String value) {
        return user -> {
            try {
                Method getter = new PropertyDescriptor(key, user.getClass()).getReadMethod();
                if (getter != null) {
                    Object valueRead = getter.invoke(user);
                    if (getter.getReturnType().equals(List.class))
                        return ((ArrayList) valueRead).stream().anyMatch(tag -> tag.toString().equalsIgnoreCase(value.toString()));
                    return valueRead.toString().equalsIgnoreCase(value);
                }

                return false;
            } catch (Exception e) {
                return false;
            }
        };
    }
}
