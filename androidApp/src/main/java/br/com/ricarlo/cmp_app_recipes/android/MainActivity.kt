package br.com.ricarlo.cmp_app_recipes.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.ricarlo.cmp_app_recipes.presentation.RecipesApp
import br.com.ricarlo.notification.core.IFcmHandler
import br.com.ricarlo.notification.getMessageData
import org.koin.android.ext.android.inject

internal class MainActivity : ComponentActivity() {
    private val fcmHandler by inject<IFcmHandler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipesApp()
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_MAIN) {
            return
        }
        fcmHandler.onClickMessage(remoteMessage = intent.getMessageData())
    }
}

@Composable
@Preview
internal fun AppPreview() {
    RecipesApp()
}
