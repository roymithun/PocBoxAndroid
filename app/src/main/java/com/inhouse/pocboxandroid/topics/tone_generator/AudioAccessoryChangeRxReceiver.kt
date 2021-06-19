package com.inhouse.pocboxandroid.topics.tone_generator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

class AudioAccessoryChangeRxReceiver(
    private val context: Context,
    private val intentFilter: IntentFilter
) :
    ObservableOnSubscribe<Intent> {
    companion object {
        const val TAG = "AudioAccessoryChangeRxReceiver"
        fun create(context: Context, intentFilter: IntentFilter): Observable<Intent> {
            return Observable
                .create(AudioAccessoryChangeRxReceiver(context, intentFilter))
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
        }
    }

    override fun subscribe(emitter: ObservableEmitter<Intent>?) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent == null) {
                    Log.d(TAG, "Broadcast received with null intent")
                    return
                }
                Log.d(TAG, "Broadcast received with action: ${intent.action}")
                emitter!!.onNext(intent)
            }
        }
        context.registerReceiver(receiver, intentFilter)
        emitter!!.setCancellable { context.unregisterReceiver(receiver) }
    }
}