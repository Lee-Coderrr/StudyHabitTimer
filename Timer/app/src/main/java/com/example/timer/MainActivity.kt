package com.example.timer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.timer.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var time = 0
    private var timerTask: Timer?= null
    private var isActivated: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {

            btnStart.setOnClickListener {
                time = (binding.etTimerMin.text.toString().toInt()*60)+(binding.etTimerSec.text.toString().toInt())
                if (time>0){
                    isActivated = true
                    startTimer()
                    viewMode(isActivated)
                }
                else{
                    time = 0
                    binding.etTimerMin.setText("00")
                    binding.etTimerSec.setText("00")
                }
            }


            btnStop.setOnClickListener {
                isActivated = false
                stopTimer()
                viewMode(isActivated)
            }
            btnReset.setOnClickListener {
                resetTimer()
            }
        }
    }

    private fun startTimer() {
        var min = binding.etTimerMin.text.toString().toInt()
        var sec = binding.etTimerSec.text.toString().toInt()

        isActivated = true

        time = (min*60)+sec
        timerTask = timer(period = 1000){
            time--
            val tMin = time/60
            val tSec = time%60
            runOnUiThread {
                binding.textTime.text = "${String.format("%02d", tMin)} : ${String.format("%02d", tSec)}"
                binding.etTimerMin.setText(String.format("%02d", tMin))
                binding.etTimerSec.setText(String.format("%02d", tSec))
            }

            if (time == 0){
                // 소리 추가
                stopTimer()
                isActivated = false

                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    viewMode(isActivated)
                }
//
//
            }
        }
    }

    private fun stopTimer() {
        timerTask?.cancel()

    }

    private fun resetTimer() {
        timerTask?.cancel()
        time = 0
        binding.etTimerSec.setText("00")
        binding.etTimerSec.setText("00")
    }

    private fun viewMode(isActivated: Boolean){
        // Start 버튼 작동
        if (isActivated == true){

            binding.etTimerMin.visibility = View.INVISIBLE
            binding.etTimerSec.visibility = View.INVISIBLE
            binding.textColon.visibility = View.INVISIBLE

            binding.textTime.visibility = View.VISIBLE

            binding.btnStop.visibility = View.VISIBLE
            binding.btnReset.visibility = View.INVISIBLE
            binding.btnStart.isEnabled = false
        }
        else {
            binding.etTimerMin.visibility = View.VISIBLE
            binding.etTimerSec.visibility = View.VISIBLE
            binding.textColon.visibility = View.VISIBLE

            binding.textTime.visibility = View.INVISIBLE

            binding.btnStop.visibility = View.INVISIBLE
            binding.btnReset.visibility = View.VISIBLE
            binding.btnStart.isEnabled = true
        }
    }


}