package com.harsain.zendesksearch;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.model.Organisation;
import com.harsain.zendesksearch.model.Ticket;
import com.harsain.zendesksearch.model.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OnLoad implements CommandLineRunner {

  private ObjectMapper objMapper;

  {
    objMapper = new ObjectMapper();
  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  @Override
  public void run(String... args) throws Exception {
    System.out.println("args are: " + args.length);
  }

  public List<User> getUsers() {
    return readJsonFile(User.class, "data/users.json");
  }

  public List<Organisation> getOrganisations() {
    return readJsonFile(Organisation.class, "data/organisations.json");
  }

  public List<Ticket> getTickets() {
    return readJsonFile(Ticket.class, "data/tickets.json");
  }

  private <T> List<T> readJsonFile(Class<T> classToConvertTo, String filePath) {
    try {
      return objMapper.readValue( new ClassPathResource(filePath).getInputStream(), listType(objMapper, classToConvertTo));
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  private <T> JavaType listType(ObjectMapper mapper, Class<T> clazz) {
    return mapper.getTypeFactory().constructCollectionType(List.class, clazz);
  }
}
