package com.thor.email.adapters.out.repository;

import com.thor.email.domain.document.MongoEmailTypeDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEmailTypeRepository extends
    MongoRepository<MongoEmailTypeDocument, ObjectId> {

}
