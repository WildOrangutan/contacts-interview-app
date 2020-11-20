package si.petermandeljc.contacts.ui.contactlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import si.petermandeljc.contacts.R
import si.petermandeljc.contacts.databinding.ContactListBinding

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.contact_list) {

	private val disposables = CompositeDisposable()
	private val viewModel: ListViewModel by viewModels()
	private val adapter: ListAdapter by lazy {
		ListAdapter(mutableListOf(), viewModel.contactClickObserver())
	}

	private lateinit var binding: ContactListBinding

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = ContactListBinding.bind(view)
		initListView()
	}

	private fun initListView() {
		val listView = binding.list
		listView.adapter = adapter
		listView.layoutManager = LinearLayoutManager(requireContext())
		val disposable = viewModel.contactsObservable()
			.bindToLifecycle(listView)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { contacts ->
				adapter.updateContacts(contacts.toList())
			}
		disposables.add(disposable)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		disposables.dispose()
	}

}