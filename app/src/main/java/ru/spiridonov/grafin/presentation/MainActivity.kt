package ru.spiridonov.grafin.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.R
import ru.spiridonov.grafin.data.memory.LevelsObjects
import ru.spiridonov.grafin.data.memory.QuestionsObjects
import ru.spiridonov.grafin.databinding.ActivityMainBinding
import ru.spiridonov.grafin.domain.usecases.LoadDataUseCase
import ru.spiridonov.grafin.presentation.viewmodels.MainViewModel
import ru.spiridonov.grafin.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val component by lazy {
        (application as GrafinApp).component
    }
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var loadDataUseCase: LoadDataUseCase
    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }
    private lateinit var dialog: AlertDialog
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                if (intent?.action == "loaded") {
                    val status = intent.getStringExtra("status")
                    //if (status == "in_progress")
                    //    dialog.show()
                    if (status == "success")
                        dialog.dismiss()
                    if (status == "error") {
                        dialog.dismiss()
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка загрузки данных",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("MenuFragment", e.message.toString())
                dialog.dismiss()
                dialog.cancel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        setProgressDialog()
        dialog.show()
        broadcastReceiver()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loadData()
        viewModel.isDataLoadedCorrect.observe(this) { result ->
            if (result) {
                println(LevelsObjects.levelsArray)
                println(QuestionsObjects.questionsArray)
                dialog.dismiss()
            } else
                Toast.makeText(this, "Error while downloading the data", Toast.LENGTH_SHORT).show()
            localBroadcastManager.unregisterReceiver(receiver)
        }
    }

    private fun broadcastReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction("loaded")
        }
        localBroadcastManager.registerReceiver(receiver, intentFilter)
    }

    private fun setProgressDialog() {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = getString(R.string.update)
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20f
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)
        dialog = builder.create()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = layoutParams
    }
}