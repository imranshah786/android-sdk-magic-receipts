package com.pollfish.magicreceipts.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pollfish.magicreceipts.MagicReceipts
import com.pollfish.magicreceipts.builder.Params
import com.pollfish.magicreceipts.listener.*
import com.pollfish.magicreceipts.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MagicReceiptsWallShowedListener,
    MagicReceiptsWallLoadedListener, MagicReceiptsWallHiddenListener,
    MagicReceiptsWallLoadFailedListener, MagicReceiptsWallShowFailedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.initButton.setOnClickListener { initSdk() }
        binding.showButton.setOnClickListener { MagicReceipts.show() }
        binding.hideButton.setOnClickListener { MagicReceipts.hide() }
        binding.checkReadinessButton.setOnClickListener { checkReadiness() }
    }

    private fun initSdk() {
        MagicReceipts.initialize(
            this,
            Params.Builder("API_KEY")
                .wallShowedListener(this)
                .wallLoadedListener(this)
                .wallHiddenListener(this)
                .wallLoadFailedListener(this)
                .wallShowFailedListener(this)
                .incentiveMode(binding.incentiveModeSwitch.isChecked)
                .userId("YOUR_USER_ID")
                .testMode(binding.testModeSwitch.isChecked)
                .build()
        )
    }

    private fun checkReadiness() {
        Toast.makeText(
            this,
            getString(R.string.check_readiness_toast_text, MagicReceipts.isReady()),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onWallDidShow() {
        Toast.makeText(this, "Panel opened", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onWallDidLoad() {
        Toast.makeText(this, "Panel loaded", Toast.LENGTH_SHORT)
            .show()
        binding.showButton.isEnabled = true
        binding.hideButton.isEnabled = true
    }

    override fun onWallDidHide() {
        Toast.makeText(this, "Panel closed", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onWallDidFailToLoad(error: MagicReceiptsLoadError) {
        Toast.makeText(this, "Load failed: $error", Toast.LENGTH_SHORT).show()
    }

    override fun onWallDidFailToShow(error: MagicReceiptsShowError) {
        Toast.makeText(this, "Show failed: $error", Toast.LENGTH_SHORT).show()
    }

}