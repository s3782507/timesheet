package com.timesheet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.timesheet.model.Timesheet;
import com.timesheet.model.User;

@Repository
public class TimesheetRepository {

    @Autowired
    private DynamoDBMapper mapper;
    
    
    public User addAndUpdateUser(User user) {
        mapper.save(user);
        return user;
    }
    
    public Timesheet addTimesheet(Timesheet timesheet) {
        mapper.save(timesheet);
        return timesheet;
    }
    
    public List<User> findUserByEmail(String email, String password) {
    	 Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
         eav.put(":val1", new AttributeValue().withS(email));
         eav.put(":val2", new AttributeValue().withS(password));

         DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
             .withFilterExpression("email = :val1 and password = :val2").withExpressionAttributeValues(eav);
         List<User> results = mapper.scan(User.class, scanExpression);
         return results;
    }
}
