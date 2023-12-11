package com.portal.PortalProject.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.portal.PortalProject.entity.UserEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CouchbaseRepository<UserEntity, String> {
    boolean existsBySsn(String ssn);
}
