package br.com.ricarlo.cmp_app_recipes.android

import android.app.Application
import android.os.Build
import android.os.StrictMode
import br.com.ricarlo.cmp_app_recipes.presentation.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .penaltyLog()
                    .build()
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                StrictMode.setVmPolicy(
                    StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .build()
                )
            }
        }
        initKoin {
            androidContext(applicationContext)
            if (BuildConfig.DEBUG) androidLogger()
        }
    }
}
