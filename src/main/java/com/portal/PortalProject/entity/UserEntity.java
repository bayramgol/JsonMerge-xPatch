package com.portal.PortalProject.entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity{
        @Id
        private String id;
        @Field(name="firstName")
        private String name;
        private String email;
        private String jobTitle;

        public JsonNode toJsonNode() {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.valueToTree(this);
        }

}
