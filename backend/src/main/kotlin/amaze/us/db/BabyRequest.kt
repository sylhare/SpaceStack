package amaze.us.db

import amaze.us.model.Decision
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.Decision.Companion.NEW
import amaze.us.model.IncomingBabyRequest
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
@Document
data class BabyRequest(@Id val id: String, val name: String = "", val status: String = DENIED,
                       val timestamp: Long = Instant.now().toEpochMilli(), val author: String = "", val decidedBy: String = "") {
  constructor() : this(id = "")
}

data class BabyUpdate(val status: String = DENIED, val decidedBy: String = "")

val IncomingBabyRequest.toBabyRequest: BabyRequest
  get() = BabyRequest(UUID.randomUUID().toString(), this.name, NEW, Instant.now().toEpochMilli(), "", "")

val Decision.toBabyUpdate: BabyUpdate
  get() = BabyUpdate(this.status, this.decidedBy)