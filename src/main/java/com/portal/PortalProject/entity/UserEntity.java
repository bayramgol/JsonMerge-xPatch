package com.portal.PortalProject.entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity{
        @Id
        @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
        private String id;
        @Field(name= "firstName")
        private String name;
        @Field
        private String email;
        @Field
        private String jobTitle;

        public JsonNode toJsonNode() {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.valueToTree(this);
        }

}
