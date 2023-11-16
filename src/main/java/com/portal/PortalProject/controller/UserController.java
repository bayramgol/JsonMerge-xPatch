package com.portal.PortalProject.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.portal.PortalProject.dto.JsonMergePatchDto;
import com.portal.PortalProject.dto.JsonPatchDto;
import com.portal.PortalProject.dto.UserDto;
import com.portal.PortalProject.entity.UserEntity;
import com.portal.PortalProject.repository.UserRepository;
import com.portal.PortalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        UserEntity user = userService.updateUser(id,updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping
    public ResponseEntity<String> applyJsonPatch(@RequestBody JsonPatchDto jsonPatchDto) throws JsonPatchException{
        try {
            String patchedJson = userService.applyJsonPatch(jsonPatchDto);
            return ResponseEntity.ok(patchedJson);
        } catch (JsonPatchException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid patch or target JSON.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error applying JSON Patch.");
        }
    }

    @PatchMapping("/merge/{id}")
    public ResponseEntity<JsonNode> applyJsonMergePatch(@PathVariable Long id, @RequestBody JsonMergePatchDto jsonMergePatchDto) {
        if (jsonMergePatchDto == null) {
            throw new IllegalArgumentException("JsonMergePatchDto must not be null");
        }

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        JsonNode mergedNode = userService.mergePatch(userEntity, jsonMergePatchDto);
        userService.updateUserJson(userEntity,jsonMergePatchDto);
        return ResponseEntity.ok(mergedNode);
    }
}
