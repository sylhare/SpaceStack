package amaze.us.service

import amaze.us.db.toBabyRequest
import amaze.us.db.toBabyUpdate
import amaze.us.model.CurrentBabyRequests
import amaze.us.model.Decision
import amaze.us.model.Decision.Companion.APPROVED
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.IncomingBabyRequest
import amaze.us.model.PopulationAmount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PopulationService {

  @Autowired
  private lateinit var babyRequestService: BabyRequestService

  companion object {
    const val ILLEGAL_CHARS = "0123456789+-*/\\|][{};:\"?><,!@#$%^&"
  }

  private var population = 2000

  fun count() = PopulationAmount((population + babyRequestService.getRequests().filter { it.status == APPROVED }.size).toString())

  fun pendingBabyRequests(): CurrentBabyRequests = CurrentBabyRequests(babyRequestService.getRequests())

  fun processNewBabyRequest(request: IncomingBabyRequest): Boolean {
    val isGoodName = request.name.none { it in ILLEGAL_CHARS } && request.name.isNotBlank()
    if (isGoodName) babyRequestService.createRequest(request.toBabyRequest)
    return isGoodName
  }

  fun processBabyRequestUpdate(id: String, decision: Decision) = when(decision.status.toLowerCase()) {
    APPROVED, DENIED -> babyRequestService.updateRequest(id, decision.toBabyUpdate)
    else -> false
  }
}