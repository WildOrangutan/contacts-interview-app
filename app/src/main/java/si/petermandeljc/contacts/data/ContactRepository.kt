package si.petermandeljc.contacts.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(
	private val cachedSource: CachedContactDataSource
) {

	fun getAll() : Collection<Contact> {
		return cachedSource.loadAll()
	}

	fun set(contact: Contact) {
		cachedSource.save(contact)
	}

}