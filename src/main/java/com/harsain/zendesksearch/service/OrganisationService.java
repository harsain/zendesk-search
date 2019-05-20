package com.harsain.zendesksearch.service;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.OrganisationResponseDto;
import com.harsain.zendesksearch.mapper.OrganisationMapper;
import com.harsain.zendesksearch.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class OrganisationService {

    @Autowired
    private OnLoad onLoad;

    public Organisation findById(String id) {
        List<Organisation> organisationList = onLoad.getOrganisations();
        List<Organisation> organisationsFiltered  = organisationList.stream().filter(o->o.get_id().equalsIgnoreCase(id)).collect(Collectors.toList());
        if (organisationsFiltered.size() > 0 ) {
            return (Organisation) organisationsFiltered.get(0);
        }
        return null;
    }

    public List<OrganisationResponseDto> findBy(String key, String value) {
        List<Organisation> organisations = onLoad.getOrganisations();
        List<Organisation> results = organisations.stream()
                .filter(new Predicate<Organisation>() {
                            @Override
                            public boolean test(Organisation organisation) {
                                try {
                                    Method getter = new PropertyDescriptor(key, organisation.getClass()).getReadMethod();
                                    if (getter != null) {
                                        Object valueRead = getter.invoke(organisation);
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

        List<OrganisationResponseDto> userResponseDtoList = new ArrayList();
        results.stream().forEach(organisation -> {
//            Organisation organisation = organisationService.findById(user.getOrganization_id());
            userResponseDtoList.add(Mappers.getMapper(OrganisationMapper.class).organisationToOrganisationResponseDto(organisation));
        });

        return userResponseDtoList;
    }
}
