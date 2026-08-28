package com.thor.email.adapters.out.repository;

import com.thor.email.domain.document.email.EmailDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEmailRepository extends
    MongoRepository<EmailDocument, ObjectId> {

}
