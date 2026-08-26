package com.thor.email.adapters.out.repository;

import com.thor.email.domain.document.email_type.EmailTypeDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEmailTypeRepository extends
    MongoRepository<EmailTypeDocument, ObjectId> {

}
