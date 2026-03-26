package com.example.feedback

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedbackFormRepository :
    MongoRepository<FeedbackFormConfig, String> {

    fun findByEnterpriseId(enterpriseId: String): FeedbackFormConfig?
}