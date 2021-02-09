package amaze.us.service

import amaze.us.model.CurrentBabyRequests
import amaze.us.model.Decision
import amaze.us.model.IncomingBabyRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ColonyHandlerService {

  @Autowired
  private lateinit var populationService: PopulationService

  fun populationAmount() = populationService.count()
  fun babyRequests(): CurrentBabyRequests = populationService.pendingBabyRequests()
  fun addBabyRequests(request: IncomingBabyRequest) = populationService.processNewBabyRequest(request)
  fun processDecision(id: String, decision: Decision) = populationService.processBabyRequestUpdate(id, decision)
}
