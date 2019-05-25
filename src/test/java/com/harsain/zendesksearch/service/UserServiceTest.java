package com.harsain.zendesksearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.UserResponseDto;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  private static Resource userResource;
  private static Resource organisationResource;
  @InjectMocks
  UserService userService;
  @Mock
  private OnLoad onLoad;
  @Mock
  private OrganisationService organisationService;
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeAll
  private static void setOnLoad() throws IOException {
    userResource = new ClassPathResource("data/users.json");
    organisationResource = new ClassPathResource("data/organizations.json");
  }

  @DisplayName("UserService is auto wired")
  @Test
  public void userServiceAutoWiredTest() {
    assertNotNull(userService);
  }

  @DisplayName("UserService.finById() when called with valid ID")
  @Test
  public void userServiceFindByIdTest() throws IOException {
    Mockito.when(onLoad.getUsers()).thenReturn(
        mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
        }));

    User user = userService.findById("1");

    assertEquals("1", user.get_id());
  }

  @DisplayName("UserService.finById() when called with invalid ID")
  @Test
  public void userServiceFindByIdNoUserTest() throws IOException {
    Mockito.when(onLoad.getUsers()).thenReturn(
        mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
        }));

    User user = userService.findById("NON_EXISTING");

    assertNull(user);
  }

  @DisplayName("UserService.findBy with a valid key & value")
  @Test
  public void userServiceFindBy() throws Exception {
    List<Organisation> organisations = mapper
        .readValue(organisationResource.getInputStream(), new TypeReference<List<Organisation>>() {
        });

    Mockito.when(organisationService.findById(ArgumentMatchers.any()))
        .thenReturn(organisations.get(0));
    Mockito.when(onLoad.getUsers()).thenReturn(
        mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
        }));

    List<UserResponseDto> users = userService.findBy("_id", "1");

    assertEquals(1, users.size());
    assertEquals("1", users.get(0).get_id());
    assertEquals(Organisation.class, users.get(0).getOrganisationObj().getClass());
  }

  @DisplayName("UserService.findBy when organisation is found")
  @Test
  public void userServiceFindByOrgNotFound() throws Exception {
    Mockito.when(organisationService.findById(ArgumentMatchers.any())).thenReturn(null);
    Mockito.when(onLoad.getUsers()).thenReturn(
        mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
        }));

    List<UserResponseDto> users = userService.findBy("_id", "1");

    assertEquals(UserResponseDto.class, users.get(0).getClass());
    assertEquals(1, users.size());
    assertEquals("1", users.get(0).get_id());
    assertNull(users.get(0).getOrganisationObj());
  }

  @DisplayName("UserService.findBy when called with invalid key")
  @Test
  public void userServiceFindByInvalidKey() throws Exception {
    Mockito.when(onLoad.getUsers()).thenReturn(
        mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
        }));

    List<UserResponseDto> users = userService.findBy("INVALID_KEY", "1");

    assertEquals(0, users.size());
  }
}
