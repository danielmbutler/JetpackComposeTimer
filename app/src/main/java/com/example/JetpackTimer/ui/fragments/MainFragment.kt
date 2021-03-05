package com.example.JetpackTimer.ui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.navigation.Navigation

import com.example.JetpackTimer.R


class MainFragment : Fragment() {


    private var TAG = "Launch Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            val navController = Navigation.findNavController(requireActivity(), R.id.main_nav_host_fragment);
            setContent {


                initContent()
            }
        }
    }







    @ExperimentalAnimationApi
    @Composable
    fun initContent(){

        var progress by remember { mutableStateOf(1f) }
        var setview: String by remember{ mutableStateOf("Choose Timer Duration") }
        var timerRunning: Boolean by remember{ mutableStateOf(false)}
        var LaunchTimer: CountDownTimer? = null
        var displayStopButton: Boolean by remember{ mutableStateOf(false)}


        @Composable
        fun StopButton(){

            OutlinedButton(
                onClick = {
                    timerRunning = false
                    progress = 0f
                    setview = "Timer Finished"
                    displayStopButton = false

                          },modifier = Modifier.padding(12.dp)
            ) {
                Text(text = "Stop Timer")

            }

        }

        fun setupTimer(TimerDuration: Long){

            if(!timerRunning){
                val LaunchTimer = object : CountDownTimer(TimerDuration, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        if(!timerRunning){
                            this.cancel()
                        } else {
                            progress -= 1000/TimerDuration.toFloat()

                            setview = "CountDown: ${millisUntilFinished / 1000}"


                            Log.d(TAG, "Minus Value ${1000/TimerDuration.toFloat()} TimerValue: $timerRunning")
                        }

                    }

                    override fun onFinish() {
                        Log.d(TAG, "LAUNCH")
                        progress = 0f
                        setview = "Timer Finished"
                        timerRunning = false

                    }

                }
                timerRunning = true
                displayStopButton = true
                LaunchTimer.start()
            }



        }



        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box() {


                val animatedProgress by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(setview,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 50.sp)
                    )

                    CircularProgressIndicator(progress = animatedProgress,
                        modifier = Modifier
                            .size(100.dp), color = Color.Red,
                    )
                    Spacer(Modifier.requiredHeight(50.dp))
                    OutlinedButton(
                        onClick = { setupTimer(10000) }, modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = "10 Seconds")

                    }
                    OutlinedButton(
                        onClick = {
                            setupTimer(30000) },modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = "30 Seconds")

                    }
                    OutlinedButton(
                        onClick = { setupTimer(60000) },modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = "1 minute")

                    }


                    AnimatedVisibility(visible = displayStopButton){
                        StopButton()
                    }

                }
            }
        }


    }
}





