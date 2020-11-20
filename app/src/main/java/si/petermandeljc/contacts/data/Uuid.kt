package si.petermandeljc.contacts.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/** value object for uuid */
@Parcelize
data class Uuid(val value: String) : Parcelable {

	companion object Factory {
		fun neu() : Uuid {
			return Uuid(UUID.randomUUID().toString())
		}
	}

}
