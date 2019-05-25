package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganisationMapper {

  OrganisationMapper INSTANCE = Mappers.getMapper(OrganisationMapper.class);

  OrganisationResponseDto organisationToOrganisationResponseDto(Organisation organisation);

  List<OrganisationResponseDto> organisationListToOrganisationResponseDtoList(
      List<Organisation> organisationList);
}
