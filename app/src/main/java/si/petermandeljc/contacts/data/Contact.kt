package si.petermandeljc.contacts.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import si.petermandeljc.contacts.utils.firstOrEmpty

@Parcelize
data class Contact(
	val uuid: Uuid,
	val name: String,
	val surname: String,
	val email: String,
	val avatarPath: String,
) :Parcelable {

	companion object Factory {
		fun neu(name: String, surname: String, email: String, avatarPath: String) : Contact {
			return Contact(uuid=Uuid.neu(), name=name, surname=surname, email=email,
				avatarPath=avatarPath)
		}
	}

	fun initials() : String {
		val nameInitial = name.firstOrEmpty()
		val surnameInitial = surname.firstOrEmpty()
		return "$nameInitial$surnameInitial"
	}

}
