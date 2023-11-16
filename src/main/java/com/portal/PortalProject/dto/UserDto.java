package com.portal.PortalProject.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.portal.PortalProject.entity.UserEntity;
import lombok.Data;

import java.util.Iterator;

import static com.couchbase.client.core.json.Mapper.convertValue;

@Data
public class UserDto {
        private Long id;
        private String name;
        private String email;
        private String jobTitle;
}
