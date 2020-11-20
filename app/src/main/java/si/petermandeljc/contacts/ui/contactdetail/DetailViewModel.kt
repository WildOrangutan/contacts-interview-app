package si.petermandeljc.contacts.ui.contactdetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import si.petermandeljc.contacts.data.Contact
import si.petermandeljc.contacts.data.ContactRepository

class DetailViewModel @ViewModelInject constructor(
	private val repository: ContactRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	companion object { const val KEY_CONTACT = "contact" }

	private val contact: Contact = savedStateHandle.get<Contact>(KEY_CONTACT)!!

	private val nameSubject = BehaviorSubject.createDefault(contact.name)
	private val disposables = CompositeDisposable()

	init {
		subscribeToName()
	}

	private fun subscribeToName() {
		val disposable = nameSubject.subscribe { name -> contact.copy(name=name)}
		addDisposable(disposable)
	}

	private fun addDisposable(disposable: Disposable) {
		disposables.add(disposable)
	}

	fun nameObserver() : Observer<String> {
		return nameSubject
	}

	fun nameObservable() : Observable<String> {
		return nameSubject.distinctUntilChanged()
	}

	override fun onCleared() {
		super.onCleared()
		disposables.clear()
		repository.set(contact)
	}

}