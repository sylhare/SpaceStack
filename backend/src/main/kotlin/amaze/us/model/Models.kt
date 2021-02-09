package amaze.us.model

import amaze.us.db.BabyRequest
import amaze.us.model.Decision.Companion.APPROVED
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

data class ListOfBabyRequest constructor(val requests: List<BabyRequest>) {

  constructor() : this(mutableListOf<BabyRequest>())

  companion object {
    fun pending(requests: MutableList<BabyRequest>) = ListOfBabyRequest(requests.filter { it.status == NEW })
    fun processed(requests: MutableList<BabyRequest>) = ListOfBabyRequest(requests.filter { it.decidedBy != "" })
    fun approvedCount(requests: MutableList<BabyRequest>): Int = requests.filter { it.status == APPROVED }.size
  }
}


