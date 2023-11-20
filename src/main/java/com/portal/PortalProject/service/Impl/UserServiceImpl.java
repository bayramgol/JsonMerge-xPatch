package com.portal.PortalProject.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.portal.PortalProject.dto.JsonMergePatchDto;
import com.portal.PortalProject.dto.JsonPatchDto;
import com.portal.PortalProject.dto.UserDto;
import com.portal.PortalProject.entity.UserEntity;
import com.portal.PortalProject.mapper.UserMapper;
import com.portal.PortalProject.repository.UserRepository;
import com.portal.PortalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private CouchbaseTemplate template;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }
    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(Long.valueOf(id));
    }

    public String applyJsonPatch(JsonPatchDto jsonPatchDto) throws JsonPatchException {
        try {
            JsonNode targetNode = objectMapper.readTree(jsonPatchDto.getTargetJson());
            JsonNode patchedNode = jsonPatchDto.getPatch().apply(targetNode);
            log.info(patchedNode);
            log.info(targetNode);
            return objectMapper.writeValueAsString(patchedNode);
        } catch (Exception e) {

            throw new JsonPatchException("Error applying JSON Patch", e);
        }
    }
    public JsonNode updateUserJson(UserEntity userEntity, JsonMergePatchDto jsonMergePatchDto){
        JsonNode mergedNode= mergePatch(userEntity,jsonMergePatchDto);
        userRepository.save(mergedNode);
        return mergedNode;
    }
    @Override
    public JsonNode mergePatch(UserEntity userEntity, JsonMergePatchDto jsonMergePatchDto) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode mergedNode = mapper.valueToTree(userEntity);

        JsonNode patch = jsonMergePatchDto.getPatch();
        Iterator<Map.Entry<String, JsonNode>> patchEntries = patch.fields();
        while (patchEntries.hasNext()) {
            Map.Entry<String, JsonNode> patchEntry = patchEntries.next();
            String key = patchEntry.getKey();
            JsonNode patchValue = patchEntry.getValue();

            if (patchValue.isNull()) {
                mergedNode.remove(key);
            } else {
                mergedNode.set(key, patchValue);
            }
        }

        return mergedNode;
    }

    public JsonNode createJsonUser(UserEntity userEntity, JsonMergePatchDto jsonMergePatchDto) {
        JsonNode jsonNode = mergePatch(userEntity,jsonMergePatchDto);
        return userRepository.save(jsonNode);
    }
}


