package com.harsain.zendesksearch.service;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import com.harsain.zendesksearch.mapper.OrganisationMapper;
import com.harsain.zendesksearch.util.FilterPredicate;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganisationService {

    @Autowired
    private OnLoad onLoad;

    private FilterPredicate filterPredicate;
    {
        filterPredicate = new FilterPredicate();
    }

    Organisation findById(String id) {
        List<Organisation> organisationList = onLoad.getOrganisations();
        List<Organisation> organisationsFiltered  = organisationList.stream().filter(o->o.get_id().equalsIgnoreCase(id)).collect(Collectors.toList());
        if (!organisationsFiltered.isEmpty() ) {
            return organisationsFiltered.get(0);
        }

        return null;
    }

    public List<OrganisationResponseDto> findBy(String key, String value) {
        List<Organisation> organisations = onLoad.getOrganisations();
        List<Organisation> results = organisations.stream()
                .filter(filterPredicate.getGenericPredicate(key, value)
                ).collect(Collectors.toList());

        List<OrganisationResponseDto> organisationResponseDtoList = new ArrayList();
        results.forEach(organisation -> {
            organisationResponseDtoList.add(Mappers.getMapper(OrganisationMapper.class).organisationToOrganisationResponseDto(organisation));
        });

        return organisationResponseDtoList;
    }
}
