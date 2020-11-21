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
import io.reactivex.rxjava3.subjects.PublishSubject
import si.petermandeljc.contacts.R
import si.petermandeljc.contacts.data.Contact
import si.petermandeljc.contacts.data.ContactRepository

class DetailViewModel @ViewModelInject constructor(
	private val repository: ContactRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	companion object {
		const val KEY_CONTACT = "contact"
		const val EMAIL_NO_ERR = -1
	}

	private var contact: Contact = savedStateHandle.get<Contact>(KEY_CONTACT)!!

	private val nameSubj = BehaviorSubject.createDefault(contact.name)
	private val surnameSubj = BehaviorSubject.createDefault(contact.surname)
	private val emailSubj = BehaviorSubject.createDefault(contact.email)
	private val emailErrSubj = BehaviorSubject.createDefault(EMAIL_NO_ERR)
	private val avatarSubj = BehaviorSubject.createDefault(contact.avatarPath)
	private val saveSubj = PublishSubject.create<Unit>()
	private val navigateBackSubj = BehaviorSubject.create<Unit>()
	private val disposables = CompositeDisposable()

	init {
		subscribeToName()
		subscribeToSurname()
		subscribeToEmail()
		subscribeToSave()
	}

	private fun subscribeToName() {
		val disposable = nameSubj.subscribe { name -> contact=contact.copy(name=name)}
		addDisposable(disposable)
	}

	private fun subscribeToSurname() {
		val disposable = surnameSubj.subscribe { surname -> contact=contact.copy(surname=surname)}
		addDisposable(disposable)
	}

	private fun subscribeToEmail() {
		val disposable = emailSubj.subscribe { email -> contact=contact.copy(email=email)}
		addDisposable(disposable)
	}

	private fun subscribeToSave() {
		val disposable = saveSubj.subscribe {
			if(validateContact()) {
				repository.set(contact)
				navigateBackSubj.onNext(Unit)
			}
		}
		addDisposable(disposable)
	}

	private fun validateContact() : Boolean {
		val validEmail = isValidEmail()
		val nextError = if(validEmail) EMAIL_NO_ERR else R.string.err_invalid_email
		emailErrSubj.onNext(nextError)
		// validate name, surname, etc.
		return validEmail
	}

	private fun isValidEmail() : Boolean {
		val email = contact.email
		return email.isNotEmpty() && email.contains("@")
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

	/** @return observable, that emits string resource id, or [EMAIL_NO_ERR] when no error */
	fun emailErrObservable() : Observable<Int> {
		return emailErrSubj.distinctUntilChanged()
	}

	fun avatarObservable() : Observable<String> {
		return avatarSubj.distinctUntilChanged()
	}

	fun saveObserver() : Observer<Unit> {
		return saveSubj
	}

	fun navigateBackObservable() : Observable<Unit> {
		return navigateBackSubj
	}

	override fun onCleared() {
		super.onCleared()
		disposables.clear()
	}

}