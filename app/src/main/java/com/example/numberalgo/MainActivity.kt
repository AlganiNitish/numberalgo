package com.example.numberalgo

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setMargins
import com.example.numberalgo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val numberText = mutableListOf<TextView>()
    private val allNumbers = (1..100).toList()
    private val fibonacciNumbers = generateFibonacciNumbers(100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        binding.btnPrime.setBackgroundColor(Color.BLUE)
        binding.btnEven.setBackgroundColor(Color.GREEN)
        binding.btnOdd.setBackgroundColor(Color.YELLOW)
        binding.btnFibonacci.setBackgroundColor(Color.RED)

        createNumbersButton()
        setUpButtonListeners()
    }
    private fun createNumbersButton(){
        binding.gridLayout.removeAllViews()
        numberText.clear()

        allNumbers.forEach{ number ->
            val text = TextView(this).apply {
                text= number.toString()
                setBackgroundColor(Color.LTGRAY)
                gravity = Gravity.CENTER
                layoutParams = GridLayout.LayoutParams().apply{
                    setMargins(8,8,8,8)
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED,1f)
                }
            }
            numberText.add(text)
            binding.gridLayout.addView(text)

        }
    }
    private fun setUpButtonListeners(){
        binding.btnPrime.setOnClickListener {highlightNumbers(this::isPrime, Color.BLUE)}
        binding.btnEven.setOnClickListener {highlightNumbers(this::isEven, Color.GREEN)}
        binding.btnOdd.setOnClickListener {highlightNumbers(this::isOdd, Color.YELLOW)}
        binding.btnFibonacci.setOnClickListener {highlightNumbers(this::isFibonnaci, Color.RED)}
    }

    private fun highlightNumbers(predicate: (Int) -> Boolean, color: Int){
        numberText.forEach{ button ->
            val number = button.text.toString().toInt()
            if( predicate(number)){
                button.setBackgroundColor(color)
            }
            else
                button.setBackgroundColor(Color.LTGRAY)

        }
    }
    private fun isPrime(number: Int): Boolean{
        if(number<=1) return false
        if(number<=3) return true
        if(number%2 == 0 || number%3 == 0 ) return false
        var i =5
        while(i*i <= number){
            if(number % i ==0 || number%(i+2) == 0) return false
            i+=6
        }
        return true
    }
    private fun isEven(number: Int) = number%2 ==0
    private fun isOdd(number: Int) = number%2!= 0
    private fun isFibonnaci(number: Int) = number in fibonacciNumbers

    private fun generateFibonacciNumbers(limit:Int):Set<Int>{
        val fibonacciNumbers = mutableSetOf<Int>()
        var a= 0
        var b= 1
        while(a<=limit){
            fibonacciNumbers.add(a)
            val temp = a
            a = b
            b = temp + a
        }
        return fibonacciNumbers
    }
}