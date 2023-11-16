package com.portal.PortalProject.dto;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.Data;

@Data
public class JsonPatchDto {
    private JsonPatch patch;
    private String targetJson;
}
