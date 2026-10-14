package com.jmdevlabs.auth0nativecomponents

import android.app.Application
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.request.DefaultClient
import com.auth0.universalcomponents.Auth0UniversalComponents
import com.auth0.universalcomponents.token.DefaultTokenProvider

class MainApplication : Application() {

    lateinit var auth0: Auth0
        private set

    lateinit var credentialsManager: CredentialsManager
        private set

    override fun onCreate() {
        super.onCreate()
        auth0 = Auth0.getInstance(
            clientId = getString(R.string.com_auth0_client_id),
            domain = getString(R.string.com_auth0_domain)
        )
        auth0.networkingClient = DefaultClient(enableLogging = true)
        credentialsManager = CredentialsManager(
            AuthenticationAPIClient(auth0),
            SharedPreferencesStorage(this)
        )
        Auth0UniversalComponents.initialize(
            context = this,
            account = auth0,
            tokenProvider = DefaultTokenProvider(credentialsManager),
            scheme = "app"
        )
    }
}
