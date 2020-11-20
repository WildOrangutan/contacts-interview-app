package si.petermandeljc.contacts.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class CachedContactDataSource @Inject constructor() { // : DataSource

	private val contacts: MutableMap<Uuid,Contact> = mutableMapOf()
	private val subject = BehaviorSubject.createDefault<Collection<Contact>>(contacts.values)

	fun save(contact: Contact) {
		contacts[contact.uuid] = contact
		subject.onNext(contacts.values)
	}

	fun load(uuid: Uuid) : Contact? {
		return contacts[uuid]
	}

	fun loadAll() : Collection<Contact> {
		return contacts.values
	}

	fun loadAllObservable() : Observable<Collection<Contact>> {
		return subject
	}

}