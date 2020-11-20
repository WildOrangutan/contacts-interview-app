package si.petermandeljc.contacts.ui.contactdetail

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.widget.textChanges
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import si.petermandeljc.contacts.R
import si.petermandeljc.contacts.databinding.ContactDetailBinding

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.contact_detail) {

	private val viewModel: DetailViewModel by viewModels()
	private lateinit var nameView: EditText
	private lateinit var avatarView: ImageView

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = ContactDetailBinding.bind(view)
		nameView = binding.name
		avatarView = binding.avatarView
		subscribeName()
		subscribeAvatar()
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


}