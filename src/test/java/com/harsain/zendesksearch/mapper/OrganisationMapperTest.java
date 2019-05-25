package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import com.harsain.zendesksearch.dto.response.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrganisationMapperTest {
    @Test
    public void testOrganisationMapping() {
        Organisation organisation = new Organisation();
        organisation.set_id("101");
        List<Organisation> organisationList = Arrays.asList(organisation);
        List<OrganisationResponseDto> organisationResponseDtoList = Mappers.getMapper(OrganisationMapper.class).organisationListToOrganisationResponseDtoList(organisationList);

        Assertions.assertNotNull(organisationResponseDtoList);
        Assertions.assertEquals(OrganisationResponseDto.class, organisationResponseDtoList.get(0).getClass());
        Assertions.assertEquals("101", organisationResponseDtoList.get(0).get_id());
    }
}
