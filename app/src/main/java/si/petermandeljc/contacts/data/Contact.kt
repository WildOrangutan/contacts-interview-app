package si.petermandeljc.contacts.data

data class Contact(
	val uuid: Uuid,
	val name: String,
	val surname: String,
	val email: String,
	val avatarPath: String,
)
