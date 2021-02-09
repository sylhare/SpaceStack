package amaze.us.service

import amaze.us.config.LOGGER
import amaze.us.db.BabyRequest
import amaze.us.db.BabyUpdate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class BabyRequestService {

  @Autowired
  private lateinit var mongoTemplate: MongoTemplate

  fun getRequests(): MutableList<BabyRequest> = try {
    mongoTemplate.findAll(BabyRequest::class.java)
  } catch (e: Exception) {
    LOGGER.error("Error while getting the requests: $e", e)
    mutableListOf()
  }

  fun createRequest(request: BabyRequest) = LOGGER.info("Baby request has been added ${mongoTemplate.insert(request)}")

  fun updateRequest(id: String, babyUpdate: BabyUpdate): Boolean {
    val update = Update().also {
      it.set("status", babyUpdate.status)
      it.set("decidedBy", babyUpdate.decidedBy)
    }
    val updatedProfile = mongoTemplate.findAndModify(Query(Criteria.where("id").`is`(id)), update, BabyRequest::class.java)
    LOGGER.info("Update for $id - $updatedProfile")
    return when (updatedProfile) {
      null -> false
      else -> true
    }
  }
}