package com.example.tempconvertor

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.example.tempconvertor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnC = binding.btnC
        val btnF = binding.btnF
        val btnConvert = binding.btnConvert
        val toggleGroup = binding.toggleGroup
        val value = binding.etTemperature.text
        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(isChecked) {
                when(checkedId) {
                    R.id.btnC -> {
                        binding.tilTemp.hint = getString(R.string.celsiusHint)
                        Toast.makeText(this, "C to F", Toast.LENGTH_SHORT).show()
                        btnConvert.setOnClickListener {
                            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                            if(value == null) {
                                Toast.makeText(this, "No value found", Toast.LENGTH_SHORT).show()
                            } else {
                                val x: Double = covertTempToF(value.toString().toDouble())
                                binding.tvAns.text = x.toString()
                            }
                        }
                    }
                    R.id.btnF -> {
                        binding.tilTemp.hint = getString(R.string.fahrenheitHint)
                        Toast.makeText(this, "F to C", Toast.LENGTH_SHORT).show()
                        btnConvert.setOnClickListener {
                            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                            if(value == null) {
                                Toast.makeText(this, "No value found", Toast.LENGTH_SHORT).show()
                            } else {
                                val x = covertTempToC(value.toString().toDouble())
                                binding.tvAns.text = x.toString()
                            }
                        }
                    }
                    else -> {
                        btnConvert.setOnClickListener {
                            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                            binding.tvAns.text = getString(R.string.error)
                            Toast.makeText(this, "Select Conversion from above", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    private fun covertTempToC(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    private fun covertTempToF(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
}