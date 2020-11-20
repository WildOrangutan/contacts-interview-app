package si.petermandeljc.contacts.utils

fun CharSequence.firstOrEmpty() : CharSequence {
	val value = firstOrNull()
	return value?.toString() ?: ""
}