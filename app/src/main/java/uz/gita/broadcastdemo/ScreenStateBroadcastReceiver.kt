package uz.gita.broadcastdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class ScreenStateBroadcastReceiver : BroadcastReceiver() {
    private var tts: TextToSpeech? = null
    private var dao: EventDao = AppDatabase.instance().eventDao()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (tts == null) {
            tts = TextToSpeech(context!!) { status ->
                if (status != TextToSpeech.ERROR) {
                    tts!!.language = Locale.US
                }
            }
        }

        val action = intent?.action
        Log.d("RRR", action.toString())
        when (action) {
            Intent.ACTION_SCREEN_OFF -> {
                event(2)
            }
            Intent.ACTION_SCREEN_ON -> {
                event(1)
            }
            Intent.ACTION_POWER_CONNECTED -> {
                Log.d("QQQ", "keldi")
                event(5)
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.d("QQQ", "Dis keldi")
                event(6)
            }
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                event(7)
            }
            Intent.ACTION_BATTERY_LOW -> {
                event(4)
            }
            Intent.ACTION_BATTERY_OKAY -> {
                event(3)
            }
        }

    }

    private fun event(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = dao.getEventById(id)
            if (data.status) {
                speak(data.name)
            }
        }
    }

    private fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}
