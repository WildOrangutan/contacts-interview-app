package si.petermandeljc.contacts.data

data class Contact(
	val uuid: Uuid,
	val name: String,
	val surname: String,
	val email: String,
	val avatarPath: String,
) {

	companion object Factory {
		fun neu(name: String, surname: String, email: String, avatarPath: String) : Contact {
			return Contact(uuid=Uuid.neu(), name=name, surname=surname, email=email,
				avatarPath=avatarPath)
		}
	}

}
