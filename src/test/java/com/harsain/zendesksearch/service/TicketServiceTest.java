package com.harsain.zendesksearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.TicketResponseDto;
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
public class TicketServiceTest {

  private static Resource userResource;
  private static Resource organisationResource;
  private static Resource ticketResource;
  @InjectMocks
  TicketService ticketService;
  @Mock
  private OnLoad onLoad;
  @Mock
  private OrganisationService organisationService;
  @Mock
  private UserService userService;
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeAll
  private static void setOnLoad() throws IOException {
    userResource = new ClassPathResource("data/users.json");
    organisationResource = new ClassPathResource("data/organizations.json");
    ticketResource = new ClassPathResource("data/tickets.json");
  }

  @DisplayName("UserService is auto wired")
  @Test
  public void userServiceAutoWiredTest() {
    assertNotNull(userService);
  }

  @DisplayName("TicketService.findBy with a valid key & value")
  @Test
  public void userServiceFindBy() throws Exception {
    List<Organisation> organisations = mapper
        .readValue(organisationResource.getInputStream(), new TypeReference<List<Organisation>>() {
        });
    List<User> users = mapper
        .readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
        });

    Mockito.when(organisationService.findById(ArgumentMatchers.any()))
        .thenReturn(organisations.get(0));
    Mockito.when(userService.findById(ArgumentMatchers.any())).thenReturn(users.get(0));
    Mockito.when(onLoad.getTickets()).thenReturn(
        mapper.readValue(ticketResource.getInputStream(), new TypeReference<List<Ticket>>() {
        }));

    List<TicketResponseDto> tickets = ticketService.findBy("tags", "utah");

    assertEquals(14, tickets.size());
    assertTrue(tickets.get(0).getTags().contains("Utah"));
    assertEquals(Organisation.class, tickets.get(0).getOrganization().getClass());
    assertEquals(User.class, tickets.get(0).getAssigneeUser().getClass());
    assertEquals(User.class, tickets.get(0).getSubmitterUser().getClass());
    assertEquals("1", tickets.get(0).getAssigneeUser().get_id());
    assertEquals("1", tickets.get(0).getSubmitterUser().get_id());
  }

  @DisplayName("TicketService.findBy when called with invalid key")
  @Test
  public void ticketServiceFindByInvalidKey() throws Exception {
    Mockito.when(onLoad.getTickets()).thenReturn(
        mapper.readValue(ticketResource.getInputStream(), new TypeReference<List<Ticket>>() {
        }));

    List<TicketResponseDto> tickets = ticketService.findBy("INVALID_KEY", "1");

    assertEquals(0, tickets.size());
  }
}
