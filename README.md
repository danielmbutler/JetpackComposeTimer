# JetpackComposeTimer
Entry for Android Dev Challenge


- Composable 

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
<h1 align="center">
  <img src="https://github.com/danielmbutler/JetpackComposeTimer/blob/master/images/JetpackComposeTimer.gif"/>
</a>
</h1>
