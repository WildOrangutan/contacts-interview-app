package si.petermandeljc.contacts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import si.petermandeljc.contacts.R
import si.petermandeljc.contacts.data.Contact
import si.petermandeljc.contacts.databinding.MainActivityBinding
import si.petermandeljc.contacts.ui.contactdetail.DetailFragment
import si.petermandeljc.contacts.ui.contactdetail.DetailViewModel.Companion.KEY_CONTACT
import si.petermandeljc.contacts.ui.contactlist.ListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = MainActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
		if(savedInstanceState == null)
			loadContactList()
	}

	private fun loadContactList() {
		loadFragment(ListFragment())
	}

	fun editContact(contact: Contact) {
		val fragment = DetailFragment()
		val bundle = Bundle()
		bundle.putParcelable(KEY_CONTACT, contact)
		fragment.arguments = bundle
		loadFragment(fragment, true)
	}

	private fun loadFragment(fragment: Fragment, backstack: Boolean=false) {
		val transaction = supportFragmentManager
			.beginTransaction()
			.replace(R.id.fragment, fragment)
		if(backstack)
			transaction.addToBackStack(null)
		transaction.commit()
	}

}