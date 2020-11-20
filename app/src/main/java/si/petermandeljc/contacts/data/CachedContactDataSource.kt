package si.petermandeljc.contacts.data

class CachedContactDataSource { // : DataSource

	private val contacts: MutableMap<Uuid,Contact> = mutableMapOf()

	fun save(contact: Contact) {
		contacts[contact.uuid] = contact
	}

	fun load(uuid: Uuid) : Contact? {
		return contacts[uuid]
	}

	fun loadAll() : Collection<Contact> {
		return contacts.values.toList()
	}

}