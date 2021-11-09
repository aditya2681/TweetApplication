package com.tweetapp.service;




import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.DatabaseSequence;



@Service
public class SequenceGeneratorService {
	
	
	@Autowired
	private MongoOperations operation;

	
	public int getSequrnce(String seqName) {
		Query q = new Query(Criteria.where("id").is(seqName));
		Update u = new Update().inc( "seq", 1);
		 DatabaseSequence counter = operation.findAndModify(q,
			      u, options().returnNew(true).upsert(true),
			      DatabaseSequence.class);
			    return !Objects.isNull(counter) ? counter.getSeq() : 1;
		
	}

}
