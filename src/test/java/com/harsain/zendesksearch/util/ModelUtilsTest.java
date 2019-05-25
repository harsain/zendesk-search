package com.harsain.zendesksearch.util;

import com.harsain.zendesksearch.exception.InvalidInputException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ModelUtilsTest {

  @InjectMocks
  ModelUtils modelUtils;

  @Test
  public void getPropertiesTestValidClass() throws InvalidInputException {
    List<String> properties = modelUtils.getPropertiesForClass(A.class);
    Assertions.assertNotNull(properties);
    Assertions.assertTrue(properties.contains("prop1"));
  }

  @Test
  public void getPropertiesTestNullClass() {
    Exception exception = Assertions.assertThrows(InvalidInputException.class, () ->
        modelUtils.getPropertiesForClass(null));
    Assertions.assertEquals("Invalid input, unable to get properties", exception.getMessage());
  }

  class A {

    private String prop1;
  }
}
