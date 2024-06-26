package com.federicocotogno.currencyconversionapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var historicalRatesRecyclerView: RecyclerView
    private lateinit var currentRateTextView: TextView
    private var currentRate: Double = 0.0
    private val historicalRatesAdapter = HistoricalRatesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historicalRatesRecyclerView = findViewById(R.id.historical_rates_recycler_view)
        currentRateTextView = findViewById(R.id.current_rate_text_view)

        historicalRatesRecyclerView.layoutManager = LinearLayoutManager(this)
        historicalRatesRecyclerView.adapter = historicalRatesAdapter

        val baseCurrency = intent.getStringExtra("BASE_CURRENCY") ?: "USD"
        val targetCurrency = intent.getStringExtra("TARGET_CURRENCY") ?: "EUR"

        getCurrentRate(baseCurrency, targetCurrency)
    }

    private fun getCurrentRate(baseCurrency: String, targetCurrency: String) {
        val apiKey = "d44c6fa6f500bd740e8640e1"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiResponse = RetrofitClient.instance.getLatestRates(apiKey, baseCurrency)
                currentRate = apiResponse.conversion_rates[targetCurrency] ?: 0.0

                withContext(Dispatchers.Main) {
                    currentRateTextView.text = "Aktualny kurs: %.3f".format(currentRate)
                    getHistoricalRates(baseCurrency, targetCurrency)
                }
            } catch (e: Exception) {
                Log.e("History", "Error: $e")
            }
        }
    }

    private fun getHistoricalRates(baseCurrency: String, targetCurrency: String) {
        val apiKey = "d44c6fa6f500bd740e8640e1"
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        val historicalRates = mutableListOf<HistoricalRate>()

        GlobalScope.launch(Dispatchers.IO) {
            for (i in 1..30) {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val date = sdf.format(calendar.time)

                try {
                    val apiResponse = RetrofitClient.instance.getHistoricalRates(apiKey, baseCurrency, date)
                    val conversionRate = apiResponse.conversion_rates[targetCurrency] ?: 0.0
                    historicalRates.add(HistoricalRate(date, conversionRate))
                } catch (e: Exception) {
                    Log.e("History", "Error: $e")
                }
            }

            withContext(Dispatchers.Main) {
                historicalRatesAdapter.setHistoricalRates(historicalRates, currentRate)
            }
        }
    }
}

data class HistoricalRate(val date: String, val rate: Double)

class HistoricalRatesAdapter : RecyclerView.Adapter<HistoricalRatesAdapter.ViewHolder>() {

    private val historicalRates = mutableListOf<HistoricalRate>()
    private var currentRate: Double = 0.0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date_text_view)
        val rateTextView: TextView = view.findViewById(R.id.rate_text_view)
        val trendImageView: ImageView = view.findViewById(R.id.trend_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historicalRate = historicalRates[position]
        holder.dateTextView.text = historicalRate.date
        holder.rateTextView.text = "Rate: %.3f".format(historicalRate.rate)
        if (historicalRate.rate < currentRate) {
            holder.trendImageView.setImageResource(R.drawable.stock_fall)
        } else {
            holder.trendImageView.setImageResource(R.drawable.stock_rise)
        }
    }

    override fun getItemCount(): Int {
        return historicalRates.size
    }

    fun setHistoricalRates(rates: List<HistoricalRate>, currentRate: Double) {
        this.currentRate = currentRate
        historicalRates.clear()
        historicalRates.addAll(rates)
        notifyDataSetChanged()
    }
}
