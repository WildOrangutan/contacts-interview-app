package si.petermandeljc.contacts.data

import javax.inject.Inject

class CachedContactDataSource @Inject constructor() { // : DataSource

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