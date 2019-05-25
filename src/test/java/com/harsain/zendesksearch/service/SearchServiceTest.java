package com.harsain.zendesksearch.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.command.OrganisationSearchCommand;
import com.harsain.zendesksearch.command.TicketSearchCommand;
import com.harsain.zendesksearch.command.UserSearchCommand;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.exception.NoUsersFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
public class SearchServiceTest {

  private static Resource userResource;
  private static Resource organisationResource;
  private static Resource ticketResource;
  @Mock
  private OnLoad onLoad;
  @Mock
  private UserSearchCommand userSearchCommand;
  @Mock
  private OrganisationSearchCommand organisationSearchCommand;
  @Mock
  private TicketSearchCommand ticketSearchCommand;
  @InjectMocks
  private SearchService searchService;
  private ObjectMapper mapper = new ObjectMapper();

  @BeforeAll
  private static void setOnLoad() throws IOException {
    userResource = new ClassPathResource("data/users.json");
    organisationResource = new ClassPathResource("data/organizations.json");
    ticketResource = new ClassPathResource("data/tickets.json");
  }

  @DisplayName("SearchService is auto wired")
  @Test
  public void searchServiceAutoWiredTest() {
    Assertions.assertNotNull(searchService);
  }

  @DisplayName("SearchService.search() when called with valid user search options")
  @Test
  public void searchServiceFindByValidUserSearchOptions() throws Exception {
    Mockito.when(userSearchCommand.search(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(
            mapper.readValue(userResource.getInputStream(), new TypeReference<List<User>>() {
            }).toString());

    String searchServiceResponse = searchService.search("user", "_id", "1");

    Assertions.assertNotNull(searchServiceResponse);
  }

  @DisplayName("SearchService.search() when called with invalid user search options")
  @Test
  public void searchServiceFindByInvalidUserSearchOptions() throws Exception {
    String expect = "NO USER FOUND";
    Mockito.when(userSearchCommand.search(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenThrow(new NoUsersFoundException(expect));

    Exception exception = Assertions.assertThrows(NoUsersFoundException.class, () ->
        searchService.search("user", "_id", "1"));
    Assertions.assertEquals(expect, exception.getMessage());
  }

  @DisplayName("SearchService.search() when called with valid organisation search options")
  @Test
  public void searchServiceFindByValidOrganisationSearchOptions() throws Exception {
    Mockito.when(organisationSearchCommand.search(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(mapper.readValue(organisationResource.getInputStream(),
            new TypeReference<List<Organisation>>() {
            }).toString());

    String searchServiceResponse = searchService.search("organisation", "_id", "1");

    Assertions.assertNotNull(searchServiceResponse);
  }

  @DisplayName("SearchService.search() when called with valid ticket search options")
  @Test
  public void searchServiceFindByValidTicketSearchOptions() throws Exception {
    Mockito.when(ticketSearchCommand.search(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(
            mapper.readValue(ticketResource.getInputStream(), new TypeReference<List<Ticket>>() {
            }).toString());

    String searchServiceResponse = searchService.search("ticket", "id", "1");

    Assertions.assertNotNull(searchServiceResponse);
  }
}
