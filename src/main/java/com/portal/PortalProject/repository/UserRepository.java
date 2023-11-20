package com.portal.PortalProject.repository;

import com.portal.PortalProject.entity.UserEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends CouchbaseRepository<UserEntity, String> {
}
