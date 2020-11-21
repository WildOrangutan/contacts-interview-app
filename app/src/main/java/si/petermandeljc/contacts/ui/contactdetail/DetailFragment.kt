package si.petermandeljc.contacts.ui.contactdetail

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import si.petermandeljc.contacts.R
import si.petermandeljc.contacts.databinding.ContactDetailBinding
import si.petermandeljc.contacts.ui.contactdetail.DetailViewModel.Companion.EMAIL_NO_ERR

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.contact_detail) {

	private val viewModel: DetailViewModel by viewModels()
	private lateinit var nameView: EditText
	private lateinit var surnameView: EditText
	private lateinit var emailView: EditText
	private lateinit var avatarView: ImageView
	private lateinit var saveView: FloatingActionButton

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		findViews()
		subscribeName()
		subscribeAvatar()
		subscribeSurname()
		subscribeEmail()
		subscribeEmailErr()
		subscribeSave()
		subscribeNavigateBack()
	}

	private fun findViews() {
		val binding = ContactDetailBinding.bind(requireView())
		nameView = binding.name
		surnameView = binding.surname
		emailView = binding.email
		avatarView = binding.avatarView
		saveView = binding.save
	}

	private fun subscribeName() {
		val view = nameView
		viewModel.nameObservable()
			.bindToLifecycle(view)
			.filter { name -> name != view.text.toString() }
			.subscribe { name -> view.setText(name) }
		view.textChanges()
			.bindToLifecycle(view)
			.map { name -> name.toString() }
			.subscribe(viewModel.nameObserver())
	}

	private fun subscribeSurname() {
		val view = surnameView
		viewModel.surnameObservable()
			.bindToLifecycle(view)
			.filter { surname -> surname != view.text.toString() }
			.subscribe { surname -> view.setText(surname) }
		view.textChanges()
			.bindToLifecycle(view)
			.map { surname -> surname.toString() }
			.subscribe(viewModel.surnameObserver())
	}

	private fun subscribeEmail() {
		val view = emailView
		viewModel.emailObservable()
			.bindToLifecycle(view)
			.filter { email -> email != view.text.toString() }
			.subscribe { email -> view.setText(email) }
		view.textChanges()
			.bindToLifecycle(view)
			.map { email -> email.toString() }
			.subscribe(viewModel.emailObserver())
	}

	private fun subscribeEmailErr() {
		val view = emailView
		viewModel.emailErrObservable()
			.bindToLifecycle(view)
			.subscribe { error -> view.error = if(error==EMAIL_NO_ERR) null else getString(error) }
	}

	private fun subscribeAvatar() {
		val view = avatarView
		viewModel.avatarObservable()
			.bindToLifecycle(view)
			.subscribe { path ->
				Glide.with(view)
					.load(path)
					.placeholder(R.drawable.avatar_placeholder)
					.circleCrop()
					.into(view)
			}
	}

	private fun subscribeSave() {
		saveView.clicks()
			.bindToLifecycle(requireView())
			.subscribe(viewModel.saveObserver())
	}

	private fun subscribeNavigateBack() {
		viewModel.navigateBackObservable()
			.subscribe { parentFragmentManager.popBackStack() }
	}

}