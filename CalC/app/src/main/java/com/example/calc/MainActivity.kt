package com.example.calc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onBackspaceClick(view: View) {
        binding.tvValues.text = binding.tvValues.text.toString().dropLast(1)
        try {
            val lastChar = binding.tvValues.text.toString().last()
            if(lastChar.isDigit()) {
                onEqual()
            }
        } catch (e: Exception) {
            binding.tvAnswer.text = ""
            binding.tvAnswer.visibility = View.GONE
            Toast.makeText(this, "Last char is not a digit", Toast.LENGTH_SHORT).show()
        }
    }


    fun onSqrtClick(view: View) {
        binding.tvValues.text = binding.tvValues.text.toString() + "√"
    }


    fun onOperatorClick(view: View) {
        if(!stateError && lastNumeric) {
            binding.tvValues.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }


    fun onEqualClick(view: View) {
        onEqual()
        binding.tvAnswer.text = binding.tvAnswer.text.toString().drop(1)
        binding.tvValues.text = ""
    }


    fun onDigitClick(view: View) {
        if(stateError) {
            binding.tvValues.text = (view as Button).text
            stateError = false
        } else {
            binding.tvValues.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }


    fun onAllClearCLick(view: View) {
        binding.tvValues.text = ""
        binding.tvAnswer.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.tvAnswer.visibility = View.GONE
    }

    private fun onEqual() {
        if(lastNumeric && !stateError) {
            val txt = binding.tvValues.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.tvAnswer.visibility = View.VISIBLE
                binding.tvAnswer.text = "$result"
            } catch (e: ArithmeticException) {
                Toast.makeText(this, "Some error occurred: $e", Toast.LENGTH_SHORT).show()
                binding.tvAnswer.text = R.string.error.toString()
                stateError = true
                lastNumeric = false
            }
        }
    }

    fun onDecClick(view: View) {
        if(binding.tvValues.text.isEmpty() || !lastNumeric) {
            binding.tvValues.text = binding.tvValues.text.toString() + "0."
        }
    }
}