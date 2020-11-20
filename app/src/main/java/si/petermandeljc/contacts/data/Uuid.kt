package si.petermandeljc.contacts.data

import java.util.*

/** value object for uuid */
data class Uuid(val value: String) {

	companion object Factory {
		fun neu() : Uuid {
			return Uuid(UUID.randomUUID().toString())
		}
	}

}
