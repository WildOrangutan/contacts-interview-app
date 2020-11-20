package si.petermandeljc.contacts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import si.petermandeljc.contacts.databinding.MainActivityBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = MainActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

}