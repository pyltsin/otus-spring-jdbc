package com.otus.jdbc.services;

import com.otus.jdbc.model.CustomSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NextSequenceServiceImpl implements  NextSequenceService {
    private final MongoOperations mongo;

    @Autowired
    public NextSequenceServiceImpl(MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public int getNextSequence(String seqName)
    {
        CustomSequence counter = mongo.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq",1),
                options().returnNew(true).upsert(true),
                CustomSequence.class);
        return counter.getSeq();
    }
}

