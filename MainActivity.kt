package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    private var Calc_screen: TextView? = null
    var lastNumeric : Boolean = false
    var lastdot : Boolean = false
// making the textview a nullable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Calc_screen = findViewById(R.id.input_field)
    }

    fun onDigit(view: View) {
        if (lastNumeric) {
            Calc_screen?.text = (view as Button).text
            lastNumeric = true
            lastdot = false
        } else {
            Calc_screen?.append((view as Button).text)
            lastNumeric = true
            lastdot = false
            // this allows digits to be appended to the screen and it also clears the previous result and appends a digit after after the = button has been pressed
        }
    }






    fun onClear(view: View){
        Calc_screen?.text = ""
        // clears the the strings in the Calc screen text view
    }

    fun onDeccimalPoint(view: View){
        if (lastNumeric && !lastdot){
            Calc_screen?.append(".")
            lastNumeric = false
            lastdot = true

        }
        // appends a dot only when a number precedes it / does not append a dot after another dot

    }
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = Calc_screen?.text.toString()

            var prefix = ""

            try{

                if (tvValue.startsWith("-")){
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")){

                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    Calc_screen?.text = removeZeroForWholeNumber((one.toDouble() - two.toDouble()).toString())

                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    Calc_screen?.text = removeZeroForWholeNumber((one.toDouble() + two.toDouble()).toString())
                }else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    Calc_screen?.text = removeZeroForWholeNumber((one.toDouble() / two.toDouble()).toString())

                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    Calc_screen?.text = removeZeroForWholeNumber((one.toDouble() * two.toDouble()).toString())

                }
            } catch (e: ArithmeticException){
        e.printStackTrace()}
        }
    }

    fun onOperator(view: View){
        Calc_screen?.text.let {

            if(lastNumeric && !isOperatorAdded(it.toString())){
                Calc_screen?.append((view as Button).text)
                lastNumeric = false
                lastdot = true
            }
        }
    }

    private fun removeZeroForWholeNumber(result: String): String {
        val value = result.toDouble()
        return if (value == value.toInt().toDouble()) {
            value.toInt().toString()
        } else {
            result
        }
    }


    private fun isOperatorAdded(value : String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}
