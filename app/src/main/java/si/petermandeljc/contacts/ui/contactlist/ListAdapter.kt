package si.petermandeljc.contacts.ui.contactlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import si.petermandeljc.contacts.data.Contact
import si.petermandeljc.contacts.databinding.ContactListItemBinding

class ListAdapter(private var contacts: List<Contact>) : RecyclerView.Adapter<Holder>() {

	fun updateContacts(newContacts: List<Contact>) {
		contacts = newContacts
//		Could calculate diff, since refreshing whole list is not the best thing
//		DiffUtil.calculateDiff()
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		val context = parent.context
		val inflater = LayoutInflater.from(context)
		val binding = ContactListItemBinding.inflate(inflater)
		return Holder(binding)
	}

	override fun onBindViewHolder(holder: Holder, position: Int) {
		val contact = contacts[position]
		holder.bind(contact)
	}

	override fun onViewRecycled(holder: Holder) {
		super.onViewRecycled(holder)
		holder.unbind()
	}

	override fun getItemCount(): Int {
		return contacts.size
	}

}

class Holder(
	private val binding: ContactListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

	private val avatar = binding.avatar
	private val title = binding.title
	private val email = binding.email
	private val context = avatar.context

	fun bind(contact: Contact) {
		Glide.with(context)
			.load(contact.avatarPath)
			.into(binding.avatar)

		// noinspection SetTextI18n - Not worth it for single space
		title.text = "${contact.name} ${contact.surname}"

		email.text = contact.email
	}

	fun unbind() {
		avatar.setImageDrawable(null)
		title.text = ""
		email.text = ""
	}

}