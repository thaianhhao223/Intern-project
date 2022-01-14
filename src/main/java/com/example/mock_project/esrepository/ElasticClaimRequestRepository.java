package com.example.mock_project.esrepository;

import com.example.mock_project.entity.ClaimRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticClaimRequestRepository extends ElasticsearchRepository<ClaimRequest,String > {
}
