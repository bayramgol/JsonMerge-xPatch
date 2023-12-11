package com.portal.PortalProject.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.portal.PortalProject.dto.JsonMergePatchDto;
import com.portal.PortalProject.dto.JsonPatchDto;
import com.portal.PortalProject.entity.UserEntity;

import java.util.List;
public interface UserService {
    List<UserEntity> getAllUsers();

    UserEntity getUserById(String id);

    UserEntity createUser(UserEntity user);

    Object deleteUser(UserEntity user, String id);

    String applyJsonPatch(UserEntity userEntity, JsonPatchDto jsonPatchDto) throws JsonPatchException;

    JsonNode mergePatch(Object userEntity, JsonMergePatchDto jsonMergePatchDto, String id);
    Long countUser();
}
