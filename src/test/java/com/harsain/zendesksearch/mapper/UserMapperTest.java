package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Test
    public void testUserMapping() {
        User user = new User();
        user.set_id("1");
        user.setOrganization_id("101");

        Organisation organisation = new Organisation();
        organisation.set_id("101");
        UserResponseDto userResponseDto = Mappers.getMapper(UserMapper.class).userToUserResponseDto(user, organisation);

        Assertions.assertEquals(Organisation.class, userResponseDto.getOrganisationObj().getClass());
        Assertions.assertEquals("101", userResponseDto.getOrganisationObj().get_id());
        Assertions.assertEquals("1", userResponseDto.get_id());
    }
}
