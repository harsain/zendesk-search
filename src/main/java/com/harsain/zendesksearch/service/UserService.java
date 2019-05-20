package com.harsain.zendesksearch.service;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.UserResponseDto;
import com.harsain.zendesksearch.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private OnLoad onLoad;
    @Autowired
    private OrganisationService organisationService;

    public User findById(String id) {
        List<User> users = onLoad.getUsers();
        List<User> usersFiltered = users.stream().filter(user -> user.get_id().equalsIgnoreCase(id)).collect(Collectors.toList());
        if (usersFiltered.size() > 0) {
            return (User) usersFiltered.get(0);
        }

        return null;
    }

    public List<UserResponseDto> findBy(String key, String value) {
        List<User> users = onLoad.getUsers();
        List<User> results = users.stream()
                .filter(new Predicate<User>() {
                            @Override
                            public boolean test(User user) {
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
                            }
                        }
                ).collect(Collectors.toList());

        List<UserResponseDto> userResponseDtoList = new ArrayList();
        results.stream().forEach(user -> {
            Organisation organisation = organisationService.findById(user.getOrganization_id());
            userResponseDtoList.add(Mappers.getMapper(UserMapper.class).userToUserResponseDto(user, organisation));
        });

        return userResponseDtoList;
    }
}
