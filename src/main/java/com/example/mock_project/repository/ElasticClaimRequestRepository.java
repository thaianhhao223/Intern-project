package com.example.mock_project.repository;

import com.example.mock_project.entity.ClaimRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticClaimRequestRepository extends ElasticsearchRepository<String, ClaimRequest> {
}
