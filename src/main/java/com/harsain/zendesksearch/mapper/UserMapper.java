package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.UserResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserMapper {

  public UserResponseDto userToUserResponseDto(User user, Organisation organisation) {
    if (user == null) {
      return null;
    }

    UserResponseDto userResponseDto = new UserResponseDto();

    userResponseDto.set_id(user.get_id());
    userResponseDto.setShared(user.getShared());
    userResponseDto.setLast_login_at(user.getLast_login_at());
    userResponseDto.setRole(user.getRole());
    userResponseDto.setSignature(user.getSignature());
    userResponseDto.setTimezone(user.getTimezone());
    userResponseDto.setVerified(user.getVerified());
    userResponseDto.setCreated_at(user.getCreated_at());
    userResponseDto.setActive(user.getActive());
    userResponseDto.setExternal_id(user.getExternal_id());
    userResponseDto.setLocale(user.getLocale());
    userResponseDto.setUrl(user.getUrl());
    userResponseDto.setSuspended(user.getSuspended());
    List<String> list = user.getTags();
    if (list != null) {
      userResponseDto.setTags(new ArrayList<String>(list));
    }
    userResponseDto.setPhone(user.getPhone());
    userResponseDto.setName(user.getName());
    userResponseDto.setAlias(user.getAlias());
    userResponseDto.setEmail(user.getEmail());
    userResponseDto.setOrganisationObj(organisation);

    return userResponseDto;
  }
}
