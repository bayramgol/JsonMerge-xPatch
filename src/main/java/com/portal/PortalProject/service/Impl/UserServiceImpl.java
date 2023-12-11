package com.portal.PortalProject.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.portal.PortalProject.dto.JsonMergePatchDto;
import com.portal.PortalProject.dto.JsonPatchDto;
import com.portal.PortalProject.entity.UserEntity;
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

    private final CouchbaseTemplate template;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Count is : {}", countUser());
        log.info(userRepository.findAll());
        return template.findByQuery(UserEntity.class).all();
    }
    public Long countUser() {
        return template.findByQuery(UserEntity.class).count();
    }
    @Override
    public UserEntity getUserById(String id) {
        return template.findById(UserEntity.class).one(id);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        boolean exists = userRepository.existsBySsn(user.getSsn());
        if(!exists) {
            return template.insertById(UserEntity.class).one(user);
        } else
            return null;
    }

    @Override
    public Object deleteUser(UserEntity user, String id) {
        template.removeById(UserEntity.class).one(id);
        return null;
    }

    public String applyJsonPatch(UserEntity userEntity,JsonPatchDto jsonPatchDto) throws JsonPatchException {
        try {
            JsonNode targetNode = objectMapper.readTree(userEntity.toJsonNode().toString());
            JsonNode patchedNode = jsonPatchDto.getPatch().apply(targetNode);
            log.info("Patched Node: {}",patchedNode.toString());
            log.info("Target Node: {}",targetNode.toString());
            return patchedNode.toPrettyString();
        } catch (Exception e) {

            throw new JsonPatchException("Error applying JSON Patch", e);
        }
    }


    @Override
    public JsonNode mergePatch(Object userEntity, JsonMergePatchDto jsonMergePatchDto,String id) {
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
}


