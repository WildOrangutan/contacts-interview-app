package si.petermandeljc.contacts.ui

import android.app.Activity
import android.os.Bundle
import si.petermandeljc.contacts.databinding.MainActivityBinding

class MainActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = MainActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

}