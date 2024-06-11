package com.polarbookshop.catalog_service.repository;

import com.polarbookshop.catalog_service.domain.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends CrudRepository<UserData, Long> {
}
