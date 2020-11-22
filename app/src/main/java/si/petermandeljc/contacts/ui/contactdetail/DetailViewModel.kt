package si.petermandeljc.contacts.ui.contactdetail

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
	@Assisted private val savedStateHandle: SavedStateHandle,
	@ApplicationContext private val context: Context
) : ViewModel() {

	companion object {
		const val KEY_CONTACT = "contact"
		private const val EMAIL_NO_ERR = ""
	}

	val nameObserver: Observer<String> get() = nameSubj
	val nameObservable: Observable<String> get() = nameSubj.distinctUntilChanged()
	val surnameObserver: Observer<String> get() = surnameSubj
	val surnameObservable: Observable<String> get() = surnameSubj.distinctUntilChanged()
	val emailObserver: Observer<String> get() = emailSubj
	val emailObservable: Observable<String> get() = emailSubj.distinctUntilChanged()
	/** @return observable, that emits error string, or [EMAIL_NO_ERR] when no error */
	val emailErrObservable: Observable<String> get() = emailErrSubj.distinctUntilChanged()
	val avatarObservable: Observable<String> get() = avatarSubj.distinctUntilChanged()
	val saveObserver: Observer<Unit> get() = saveSubj
	val navigateBackObservable: Observable<Unit> get() = navigateBackSubj

	private var contact: Contact = savedStateHandle.get<Contact>(KEY_CONTACT)!!

	private val nameSubj = BehaviorSubject.createDefault(contact.name)
	private val surnameSubj = BehaviorSubject.createDefault(contact.surname)
	private val emailSubj = BehaviorSubject.createDefault(contact.email)
	private val emailErrSubj = BehaviorSubject.createDefault(EMAIL_NO_ERR)
	private val avatarSubj = BehaviorSubject.createDefault(contact.avatarPath)
	private val saveSubj = PublishSubject.create<Unit>()
	private val navigateBackSubj = BehaviorSubject.create<Unit>()
	private val disposables = CompositeDisposable()

	private val emailError = context.getString(R.string.err_invalid_email)

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

	private fun addDisposable(disposable: Disposable) {
		disposables.add(disposable)
	}

	private fun validateContact() : Boolean {
		val validEmail = isValidEmail()
		val nextError = if(validEmail) EMAIL_NO_ERR else emailError
		emailErrSubj.onNext(nextError)
		// validate name, surname, etc.
		return validEmail
	}

	private fun isValidEmail() : Boolean {
		val email = contact.email
		return email.isNotEmpty() && email.contains("@")
	}

	override fun onCleared() {
		super.onCleared()
		disposables.clear()
	}

}