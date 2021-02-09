package amaze.us

import amaze.us.db.BabyRequest
import amaze.us.model.Decision.Companion.APPROVED
import amaze.us.model.Decision.Companion.NEW
import amaze.us.model.PopulationAmount
import amaze.us.service.BabyRequestService
import amaze.us.service.PopulationService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class BabyRequestServiceTest {

  @MockkBean(relaxed = true)
  private lateinit var mongoTemplate: MongoTemplate

  @Autowired
  private lateinit var babyRequestService: BabyRequestService

  @Autowired
  private lateinit var populationService: PopulationService

  @Test
  fun getRequestsWithApprovedTest() {
    val defaultResponse = mutableListOf(BabyRequest("", "", APPROVED), BabyRequest("", "", NEW))
    every { mongoTemplate.findAll(BabyRequest::class.java) } returns defaultResponse
    assertEquals(defaultResponse, babyRequestService.getRequests())
    assertEquals(PopulationAmount("2001"), populationService.count())
  }

  @Test
  fun getCurrentRequestsTest() {
    val defaultResponse = mutableListOf(BabyRequest("", "", APPROVED), BabyRequest("", "", NEW))
    every { mongoTemplate.findAll(BabyRequest::class.java) } returns defaultResponse
    assertEquals(1, populationService.pendingBabyRequests().requests.size)
  }
}