package si.petermandeljc.contacts.ui.contactlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import si.petermandeljc.contacts.R
import si.petermandeljc.contacts.data.Contact
import si.petermandeljc.contacts.databinding.ContactListBinding
import si.petermandeljc.contacts.ui.MainActivity

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.contact_list) {

	private val viewModel: ListViewModel by viewModels()
	private val adapter: ListAdapter by lazy {
		ListAdapter(mutableListOf(), viewModel.contactClickObserver)
	}

	private lateinit var binding: ContactListBinding

	private val mainActivity get() = requireActivity() as MainActivity

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding = ContactListBinding.bind(view)
		initListView()
		subscribeToEditContact()
	}

	private fun initListView() {
		val listView = binding.list
		listView.adapter = adapter
		listView.layoutManager = LinearLayoutManager(requireContext())
		viewModel.contactsObservable
			.bindToLifecycle(listView)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { contacts ->
				adapter.updateContacts(contacts.toList())
			}
	}

	private fun subscribeToEditContact() {
		viewModel.editContactObservable
			.bindToLifecycle(requireView())
			.subscribe { contact -> editContact(contact) }
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.contact_list_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if(item.itemId == R.id.menu_new_contact) {
			viewModel.addContactObserver.onNext(Unit)
			return true
		}
		return false
	}

	private fun editContact(contact: Contact) {
		mainActivity.editContact(contact)
	}

}