package com.harsain.zendesksearch.dto.response;

import com.harsain.zendesksearch.dto.Organisation;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String _id;
    private String shared;
    private String last_login_at;
    private String role;
    private String signature;
    private String timezone;
    private String verified;
    private String created_at;
    private String active;
    private String external_id;
    private String locale;
    private String url;
    private String suspended;
    private List<String> tags;
    private String phone;
    private Organisation organisationObj;
    private String name;
    private String alias;
    private String email;
}
