package com.harsain.zendesksearch.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private OnLoad onLoad;
    @Mock
    private OrganisationService organisationService;

    @InjectMocks
    UserService userService;

    private ObjectMapper mapper = new ObjectMapper();
    private static Resource userResource;

    @BeforeClass
    public static void setOnLoad() throws IOException {
        userResource = new ClassPathResource("data/users.json");
    }

    @Test
    public void userServiceAutowired() {
        assertNotNull(userService);
    }

    @Test
    public void userServiceTestFindById() throws IOException {
        Mockito.when(onLoad.getUsers()).thenReturn(mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>(){}));
        User user = userService.findById("1");
        assertEquals("1", user.get_id());
    }

    @Test
    public void userServiceTestFindByIdNoUser() throws IOException {
        Mockito.when(onLoad.getUsers()).thenReturn(mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>(){}));

        User user = userService.findById("NON_EXISTING");
        assertNull(user);
    }
}
