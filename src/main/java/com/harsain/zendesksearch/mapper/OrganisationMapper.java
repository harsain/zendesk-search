package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.OrganisationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganisationMapper {
    OrganisationMapper INSTANCE = Mappers.getMapper(OrganisationMapper.class);

    OrganisationResponseDto organisationToOrganisationResponseDto(Organisation organisation);
}
