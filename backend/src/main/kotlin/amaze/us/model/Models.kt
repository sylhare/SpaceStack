package amaze.us.model

import amaze.us.db.BabyRequest
import amaze.us.model.Decision.Companion.DENIED
import amaze.us.model.Decision.Companion.NEW
import java.util.*

data class PopulationAmount(val amount: String) {
  constructor() : this("N/A")
}

data class IncomingBabyRequest(val name: String, val author: String) {
  constructor() : this("N/A", "")
}

data class ProcessingBabyRequest(val name: String, val id: String = UUID.randomUUID().toString(), val status: String = NEW, val author: String) {
  constructor() : this("N/A", "null-${UUID.randomUUID()}", DENIED, "")
}

data class Decision(val status: String, val decidedBy: String) {
  constructor() : this(DENIED, "")

  companion object {
    const val NEW = "new"
    const val DENIED = "denied"
    const val APPROVED = "approved"
  }
}

class CurrentBabyRequests {
  var requests: List<BabyRequest>

  constructor(requests: MutableList<BabyRequest>) {
    this.requests = requests.filter { it.status == NEW }
  }

  constructor() : this(mutableListOf<BabyRequest>())

  override fun hashCode() = requests.hashCode()
  override fun equals(other: Any?) = this === other ||
      other is CurrentBabyRequests && this.requests == other.requests
}


