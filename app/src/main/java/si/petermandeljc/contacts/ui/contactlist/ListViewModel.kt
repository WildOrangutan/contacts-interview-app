package si.petermandeljc.contacts.ui.contactlist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import si.petermandeljc.contacts.data.Contact
import si.petermandeljc.contacts.data.ContactRepository

class ListViewModel @ViewModelInject constructor(
	private val repository: ContactRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val contacts: MutableCollection<Contact> = mutableListOf()
	private val contactsSubj = BehaviorSubject.create<Collection<Contact>>()

	init {
		Observable
			.fromCallable { repository.getAll() }
			.observeOn(AndroidSchedulers.mainThread())
			.take(1)
			.subscribe { loadedContacts ->
				contacts.clear()
				contacts.addAll(loadedContacts)
				contactsSubj.onNext(contacts)
			}
	}

	fun contactsObservable() : Observable<Collection<Contact>> {
		return contactsSubj
	}

}