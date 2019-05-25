package com.harsain.zendesksearch;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OnLoad implements CommandLineRunner {

  private ObjectMapper objMapper;
  private List<User> users = new ArrayList<>();
  private List<Organisation> organisations = new ArrayList<>();
  private List<Ticket> tickets = new ArrayList<>();

  {
    objMapper = new ObjectMapper();
    objMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  @Override
  public void run(String... args) throws Exception {
  }

  public List<User> getUsers() {
    if (users.isEmpty()) {
      users = readJsonFile(User.class, "data/users.json");
    }

    return users;
  }

  public List<Organisation> getOrganisations() {
    if (organisations.isEmpty()) {
      organisations = readJsonFile(Organisation.class, "data/organizations.json");
    }

    return organisations;
  }

  public List<Ticket> getTickets() {
    if (tickets.isEmpty()) {
      tickets = readJsonFile(Ticket.class, "data/tickets.json");
    }

    return tickets;
  }

  private <T> List<T> readJsonFile(Class<T> classToConvertTo, String filePath)
      throws RuntimeException {
    try {
      return objMapper.readValue(new ClassPathResource(filePath).getInputStream(),
          listType(objMapper, classToConvertTo));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Unable to read JSON file from file path: %s", filePath));
    }
  }

  private <T> JavaType listType(ObjectMapper mapper, Class<T> clazz) {
    return mapper.getTypeFactory().constructCollectionType(List.class, clazz);
  }
}
