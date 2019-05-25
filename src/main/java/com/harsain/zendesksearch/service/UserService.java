package com.harsain.zendesksearch.service;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.UserResponseDto;
import com.harsain.zendesksearch.mapper.UserMapper;
import com.harsain.zendesksearch.util.FilterPredicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private OnLoad onLoad;
  @Autowired
  private OrganisationService organisationService;

  private FilterPredicate filterPredicate;

  {
    filterPredicate = new FilterPredicate();
  }

  public User findById(String id) {
    List<User> users = onLoad.getUsers();
    List<User> usersFiltered = users.stream().filter(user -> user.get_id().equalsIgnoreCase(id))
        .collect(Collectors.toList());
    if (!usersFiltered.isEmpty()) {
      return usersFiltered.get(0);
    }

    return null;
  }

  public List<UserResponseDto> findBy(String key, String value) {
    List<User> results = onLoad.getUsers()
        .stream()
        .filter(filterPredicate.getGenericPredicate(key, value))
        .collect(Collectors.toList());

    ArrayList<UserResponseDto> userResponseDtoList = new ArrayList<>();
    results.forEach(user -> {
      Organisation organisation = organisationService.findById(user.getOrganization_id());
      userResponseDtoList
          .add(Mappers.getMapper(UserMapper.class).userToUserResponseDto(user, organisation));
    });

    return userResponseDtoList;
  }
}
