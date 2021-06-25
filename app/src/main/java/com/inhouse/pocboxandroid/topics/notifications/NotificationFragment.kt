package com.inhouse.pocboxandroid.topics.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.inhouse.pocboxandroid.MainActivity
import com.inhouse.pocboxandroid.R
import com.inhouse.pocboxandroid.databinding.FragmentNotificationBinding

const val CHANNEL_ID_BLUE = "blue_channel"
const val CHANNEL_ID_RED = "red_channel"

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureBlueChannel()
        configureRedChannel()
    }

    private fun configureBlueChannel() {
        binding.btnCreateBlueNotificationChannel.setOnClickListener {
            val name = getString(R.string.channel_name_blue)
            val descriptionText = getString(R.string.channel_description_blue)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelCompat = NotificationChannelCompat.Builder(CHANNEL_ID_BLUE, importance)
                .setName(name)
                .setDescription(descriptionText)
                .build()

            NotificationManagerCompat.from(requireContext())
                .apply { createNotificationChannel(channelCompat) }
        }
        binding.btnShowBlueNotification.setOnClickListener {
            createBlueNotification()
        }
        binding.btnShowBlueNotificationWithTap.setOnClickListener {
            createBlueNotificationWithAction()
        }
        binding.btnOpenBlueNotificationChannelSettings.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                    .apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                        putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID_BLUE)
                    }.also { startActivity(it) }
            }
        }
        binding.btnDeleteBlueNotificationChannelSettings.setOnClickListener {
            NotificationManagerCompat.from(requireContext()).apply {
                deleteNotificationChannel(CHANNEL_ID_BLUE)
            }
        }
    }

    private fun configureRedChannel() {
        binding.btnCreateRedNotificationChannel.setOnClickListener {
            val name = getString(R.string.channel_name_red)
            val descriptionText = getString(R.string.channel_description_red)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelCompat = NotificationChannelCompat.Builder(CHANNEL_ID_RED, importance)
                .setName(name)
                .setDescription(descriptionText)
                .build()

            NotificationManagerCompat.from(requireContext())
                .apply { createNotificationChannel(channelCompat) }
        }
        binding.btnShowRedNotification.setOnClickListener {
            createRedNotification()
        }
        binding.btnOpenRedNotificationChannelSettings.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                    .apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                        putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID_RED)
                    }.also { startActivity(it) }
            }
        }
        binding.btnDeleteRedNotificationChannelSettings.setOnClickListener {
            NotificationManagerCompat.from(requireContext()).apply {
                deleteNotificationChannel(CHANNEL_ID_RED)
            }
        }
    }

    private fun createBlueNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID_BLUE)
            .setSmallIcon(R.drawable.poc_icon)
            .setContentTitle("Blue Notification Test")
            .setContentText("This is a blue notification test with small text.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("This is a notification test with big text. It should ideally expand over next line")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(1234, builder.build())
        }
    }

    private fun createBlueNotificationWithAction() {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, 0)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID_BLUE)
            .setSmallIcon(R.drawable.poc_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(requireContext())) {
            notify(12345, builder.build())
        }
    }

    private fun createRedNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID_RED)
            .setSmallIcon(R.drawable.poc_icon)
            .setContentTitle("Red Notification Test")
            .setContentText("This is a red notification test with small text.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("This is a notification test with big text. It should ideally expand over next line")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(2345, builder.build())
        }
    }
}