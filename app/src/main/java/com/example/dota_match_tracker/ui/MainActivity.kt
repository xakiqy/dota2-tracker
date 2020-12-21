package com.example.dota_match_tracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.dota_match_tracker.R
import com.example.dota_match_tracker.databinding.ActivityMainBinding
import com.example.dota_match_tracker.worker.DotaInfoWorker

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        val workManager = WorkManager.getInstance()
        syncNewDotaData(workManager)
        val outputWorkInfo = workManager.getWorkInfosByTagLiveData("Work")
        outputWorkInfo.observe(this, {
            val workInfo = it[0]
            if (workInfo.state == WorkInfo.State.FAILED) {
                startActivity(Intent(this, FailActivity::class.java))
            }
        })
    }

    private fun syncNewDotaData(workManager: WorkManager) {
        val dotaDataWork = OneTimeWorkRequestBuilder<DotaInfoWorker>()
            .addTag("Work").build()
        workManager.enqueue(dotaDataWork)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}