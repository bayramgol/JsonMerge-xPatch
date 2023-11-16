package com.portal.PortalProject.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class JsonMergePatchDto {
    JsonNode patch;
}
