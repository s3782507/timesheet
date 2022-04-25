package com.timesheet.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

	@Value("$aws.accesskey")
    private String accessKey;
	
	@Value("$aws.secretkey")
    private String secretKey;

	@Value("$aws.service.endpoint")
    private String serviceEndpoint;
	
	@Value("$aws.region")
    private String region;
    

    @Bean
    public DynamoDBMapper mapper() {
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }
    
    private AmazonDynamoDB amazonDynamoDBConfig() {
        return AmazonDynamoDBClientBuilder.standard()
        		.withRegion(Regions.US_EAST_1)
        		.withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        		.build();
    }
}