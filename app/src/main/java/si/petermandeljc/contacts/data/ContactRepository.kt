package si.petermandeljc.contacts.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

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