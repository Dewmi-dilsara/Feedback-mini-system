package com.example.feedback

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedbackRequestRepository :
    MongoRepository<FeedbackRequest, String>