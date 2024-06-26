package com.federicocotogno.currencyconversionapi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var baseCurrencySpinner: Spinner
    private lateinit var targetCurrencySpinner: Spinner
    private lateinit var amountInput: EditText
    private lateinit var resultTextView: TextView
    private lateinit var swapButton: ImageView
    private lateinit var baseFlag: ImageView
    private lateinit var targetFlag: ImageView
    private lateinit var historyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseCurrencySpinner = findViewById(R.id.base_currency_spinner)
        targetCurrencySpinner = findViewById(R.id.target_currency_spinner)
        amountInput = findViewById(R.id.amount_input)
        resultTextView = findViewById(R.id.result_text_view)
        swapButton = findViewById(R.id.swap_button)
        baseFlag = findViewById(R.id.base_flag)
        targetFlag = findViewById(R.id.target_flag)
        historyButton = findViewById(R.id.history_button)

        setupSpinners()
        setupTextWatchers()
        setupSwapButton()
        setupHistoryButton()
    }

    private fun setupSpinners() {
        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        baseCurrencySpinner.adapter = adapter
        targetCurrencySpinner.adapter = adapter

        baseCurrencySpinner.setSelection(adapter.getPosition("PLN"))
        targetCurrencySpinner.setSelection(adapter.getPosition("EUR"))

        baseCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (amountInput.text.isNotEmpty()) {
                    getConversionResult()
                }
                baseFlag.setImageResource(getFlagResource(baseCurrencySpinner.selectedItem.toString()))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        targetCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (amountInput.text.isNotEmpty()) {
                    getConversionResult()
                }
                targetFlag.setImageResource(getFlagResource(targetCurrencySpinner.selectedItem.toString()))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupTextWatchers() {
        amountInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty()) {
                    getConversionResult()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupSwapButton() {
        swapButton.setOnClickListener {
            val basePosition = baseCurrencySpinner.selectedItemPosition
            baseCurrencySpinner.setSelection(targetCurrencySpinner.selectedItemPosition)
            targetCurrencySpinner.setSelection(basePosition)
        }
    }

    private fun setupHistoryButton() {
        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("BASE_CURRENCY", baseCurrencySpinner.selectedItem.toString())
            intent.putExtra("TARGET_CURRENCY", targetCurrencySpinner.selectedItem.toString())
            startActivity(intent)
        }
    }

    private fun getConversionResult() {
        val baseCurrency = baseCurrencySpinner.selectedItem.toString()
        val targetCurrency = targetCurrencySpinner.selectedItem.toString()
        val amount = amountInput.text.toString().toFloatOrNull() ?: return

        val apiKey = "d44c6fa6f500bd740e8640e1"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiResponse = RetrofitClient.instance.getLatestRates(apiKey, baseCurrency)
                val conversionRate = apiResponse.conversion_rates[targetCurrency] ?: 0.0
                val convertedAmount = amount * conversionRate

                withContext(Dispatchers.Main) {
                    resultTextView.text = "Kwota po przewalutowaniu = %.3f \nKurs 1:1 = %.3f".format(convertedAmount, conversionRate)
                }
            } catch (e: Exception) {
                Log.e("Main", "Error: $e")
                withContext(Dispatchers.Main) {
                    resultTextView.text = "Failed to retrieve data"
                }
            }
        }
    }

    private fun getFlagResource(currencyCode: String): Int {
        return when (currencyCode) {
            "USD" -> R.drawable.flag_usd
            "EUR" -> R.drawable.flag_eur
            "AUD" -> R.drawable.flag_aud
            "BIF" -> R.drawable.flag_bif
            "CAD" -> R.drawable.flag_cad
            "DKK" -> R.drawable.flag_dkk
            "FJD" -> R.drawable.flag_fjd
            "GBP" -> R.drawable.flag_gbp
            "JPY" -> R.drawable.flag_jpy
            "KYD" -> R.drawable.flag_kyd
            "MAD" -> R.drawable.flag_mad
            "NGN" -> R.drawable.flag_ngn
            "PLN" -> R.drawable.flag_pln
            "QAR" -> R.drawable.flag_qar
            "SEK" -> R.drawable.flag_sek
            "SSP" -> R.drawable.flag_ssp
            "VES" -> R.drawable.flag_ves
            "ZWL" -> R.drawable.flag_zwl
            else -> R.drawable.flag_placeholder
        }
    }
}
