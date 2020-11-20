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

	private val nameSubj = BehaviorSubject.createDefault(contact.name)
	private val surnameSubj = BehaviorSubject.createDefault(contact.surname)
	private val emailSubj = BehaviorSubject.createDefault(contact.email)
	private val avatarSubj = BehaviorSubject.createDefault(contact.avatarPath)
	private val disposables = CompositeDisposable()

	init {
		subscribeToName()
		subscribeToSurname()
		subscribeToEmail()
	}

	private fun subscribeToName() {
		val disposable = nameSubj.subscribe { name -> contact.copy(name=name)}
		addDisposable(disposable)
	}

	private fun subscribeToSurname() {
		val disposable = nameSubj.subscribe { surname -> contact.copy(surname=surname)}
		addDisposable(disposable)
	}

	private fun subscribeToEmail() {
		val disposable = emailSubj.subscribe { email -> contact.copy(email=email)}
		addDisposable(disposable)
	}

	private fun addDisposable(disposable: Disposable) {
		disposables.add(disposable)
	}

	fun nameObserver() : Observer<String> {
		return nameSubj
	}

	fun nameObservable() : Observable<String> {
		return nameSubj.distinctUntilChanged()
	}

	fun surnameObserver() : Observer<String> {
		return surnameSubj
	}

	fun surnameObservable() : Observable<String> {
		return surnameSubj.distinctUntilChanged()
	}

	fun emailObserver() : Observer<String> {
		return emailSubj
	}

	fun emailObservable() : Observable<String> {
		return emailSubj.distinctUntilChanged()
	}

	fun avatarObservable() : Observable<String> {
		return avatarSubj.distinctUntilChanged()
	}

	override fun onCleared() {
		super.onCleared()
		disposables.clear()
		repository.set(contact)
	}

}