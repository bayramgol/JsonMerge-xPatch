package com.portal.PortalProject.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.portal.PortalProject.dto.JsonMergePatchDto;
import com.portal.PortalProject.dto.JsonPatchDto;
import com.portal.PortalProject.dto.UserDto;
import com.portal.PortalProject.entity.UserEntity;

import java.util.List;
public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    UserEntity createUser(UserEntity user);
    UserEntity updateUser(Long id, UserEntity updatedUser);
    void deleteUser(Long id);
    String applyJsonPatch(JsonPatchDto jsonPatchDto) throws JsonPatchException;
    JsonNode mergePatch(UserEntity userEntity, JsonMergePatchDto jsonMergePatchDto);
    JsonNode updateUserJson(UserEntity userEntity, JsonMergePatchDto jsonMergePatchDto);
}
