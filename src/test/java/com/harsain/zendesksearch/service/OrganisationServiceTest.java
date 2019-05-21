package com.harsain.zendesksearch.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrganisationServiceTest {
    @Mock
    private OnLoad onLoad;

    @InjectMocks
    private OrganisationService organisationService;

    private ObjectMapper mapper = new ObjectMapper();
    private static Resource organisationResponse;

    @BeforeAll
    private static void setOnLoad() throws Exception {
        organisationResponse = new ClassPathResource("data/organizations.json");
    }

    @DisplayName("OrganisationService is auto wired")
    @Test
    public void organisationServiceAutoWiredTest() {
        Assertions.assertNotNull(organisationService);
    }

    @DisplayName("OrganisationService.findBy() when called with valid ID")
    @Test
    public void orgServiceFindByValidId() throws Exception {
        Mockito.when(onLoad.getOrganisations()).thenReturn(mapper.readValue(organisationResponse.getInputStream(), new TypeReference<List<Organisation>>(){}));

        Organisation org = organisationService.findById("101");

        Assertions.assertNotNull(org);
        Assertions.assertEquals("101", org.get_id());
    }

    @DisplayName("OrganisationService.findBy() is called with invalid ID")
    @Test
    public void orgServiceFindByInvalidId() throws Exception {
        Organisation org = organisationService.findById("NOT_VALID");

        Assertions.assertNull(org);
    }

    @DisplayName("OrganisationService.findBy() with a valid key & value")
    @Test
    public void orgServiceFindBy() throws Exception {
        List<Organisation> organisations = mapper.readValue(organisationResponse.getInputStream(), new TypeReference<List<Organisation>>(){});

        Mockito.when(onLoad.getOrganisations()).thenReturn(mapper.readValue(organisationResponse.getInputStream(), new TypeReference<List<Organisation>>(){}));

        List<OrganisationResponseDto> orgs = organisationService.findBy("_id", "101");

        Assertions.assertEquals(1, orgs.size());
        Assertions.assertEquals("101", orgs.get(0).get_id());
        Assertions.assertEquals(OrganisationResponseDto.class, orgs.get(0).getClass());
    }

    @DisplayName("OrganisationService.findBy() with key of type array")
    @Test
    public void orgServiceFindByInAListKey() throws Exception {
        List<Organisation> organisations = mapper.readValue(organisationResponse.getInputStream(), new TypeReference<List<Organisation>>(){});

        Mockito.when(onLoad.getOrganisations()).thenReturn(mapper.readValue(organisationResponse.getInputStream(), new TypeReference<List<Organisation>>(){}));

        List<OrganisationResponseDto> orgs = organisationService.findBy("tags", "Wall");

        Assertions.assertEquals(1, orgs.size());
        Assertions.assertEquals("117", orgs.get(0).get_id());
        Assertions.assertEquals(OrganisationResponseDto.class, orgs.get(0).getClass());
        Assertions.assertTrue(orgs.get(0).getTags().contains("Wall"));
    }

    @DisplayName("OrganisationService.findBy() with invalid key")
    @Test
    public void orgServiceFindByInvalidKey() throws Exception {
        Mockito.when(onLoad.getOrganisations()).thenReturn(mapper.readValue(organisationResponse.getInputStream(), new TypeReference<List<Organisation>>(){}));

        List<OrganisationResponseDto> orgs = organisationService.findBy("INVALID_KEY", "Wall");

        Assertions.assertEquals(0, orgs.size());
    }
}
