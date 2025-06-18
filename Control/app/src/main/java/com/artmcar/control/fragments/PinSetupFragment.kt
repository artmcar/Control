package com.artmcar.control.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.artmcar.control.R
import com.artmcar.control.databinding.FragmentPinSetupBinding


class PinSetupFragment : Fragment(), TextWatcher {
    private var _binding: FragmentPinSetupBinding? = null
    private val binding get() = _binding!!
    private val editTextArray = ArrayList<EditText>(NUM_OF_DIGITS)
    private var numTemp = ""

    companion object {
        private const val NUM_OF_DIGITS = 4
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.clearButtonSetup.setOnClickListener {
            initCodeEditTexts()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        val layout = binding.codeLayoutSetup

        for (index in 0 until layout.childCount) {
            val v = layout.getChildAt(index)
            if (v is EditText) {
                editTextArray.add(v)
                v.addTextChangedListener(this)
                v.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN && index > 0) {
                        editTextArray[index - 1].requestFocus()
                    }
                    false
                }
            }
        }

        editTextArray[0].requestFocus()
    }

    private fun initCodeEditTexts() {

        for (i in 0 until editTextArray.size)
            editTextArray[i].setText("")

        editTextArray[0].requestFocus()

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        numTemp = s.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        for (i in editTextArray.indices) {
            if (s === editTextArray[i].editableText) {
                if (s.isNullOrBlank()) return
                if (s.length >= 2) {
                    val lastChar = s.toString().last().toString()
                    editTextArray[i].setText(lastChar)
                } else if (i < editTextArray.size - 1) {
                    editTextArray[i + 1].requestFocus()
                }

                if (allDigitsEntered()) {
                    val pin = getCode()
                    savePin(pin)
                }
                break
            }
        }
    }

    private fun allDigitsEntered(): Boolean {
        return editTextArray.all { it.text.toString().isNotBlank() }
    }

    private fun getCode(): String {
        return editTextArray.joinToString("") { it.text.toString().trim() }
    }

    private fun savePin(pin: String) {
        val prefs = SecurePrefsUtil.getSecurePrefs(requireContext())
        prefs.edit()
            .putString("pin", pin)
            .putBoolean("locked", true)
            .apply()

        Toast.makeText(requireContext(), R.string.set_pin, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.mainFragment)
    }


}