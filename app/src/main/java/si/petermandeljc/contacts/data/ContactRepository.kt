package si.petermandeljc.contacts.data

import io.reactivex.rxjava3.core.Observable
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

	fun getAllObservable() : Observable<Collection<Contact>> {
		return cachedSource.loadAllObservable()
	}


}