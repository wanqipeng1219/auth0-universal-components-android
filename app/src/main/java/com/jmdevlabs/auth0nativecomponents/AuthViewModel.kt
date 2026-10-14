package com.jmdevlabs.auth0nativecomponents

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface AuthState {
    data object Loading : AuthState
    data object LoggedOut : AuthState
    data class LoggedIn(val userProfile: UserProfile) : AuthState
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as MainApplication

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    init {
        checkSession()
    }

    private fun checkSession() {
        if (!app.credentialsManager.hasValidCredentials()) {
            _authState.value = AuthState.LoggedOut
            return
        }
        app.credentialsManager.getCredentials(object : Callback<Credentials, CredentialsManagerException> {
            override fun onSuccess(result: Credentials) {
                fetchUserProfile(result.accessToken)
            }
            override fun onFailure(error: CredentialsManagerException) {
                _authState.value = AuthState.LoggedOut
            }
        })
    }

    fun login(activity: Activity) {
        WebAuthProvider.login(app.auth0)
            .withScheme("app")
            .withScope("openid profile email offline_access")
            .start(activity, object : Callback<Credentials, AuthenticationException> {
                override fun onSuccess(result: Credentials) {
                    app.credentialsManager.saveCredentials(result)
                    fetchUserProfile(result.accessToken)
                }
                override fun onFailure(error: AuthenticationException) {
                    _authState.value = AuthState.LoggedOut
                }
            })
    }

    fun logout(activity: Activity) {
        WebAuthProvider.logout(app.auth0)
            .withScheme("app")
            .start(activity, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    app.credentialsManager.clearCredentials()
                    _authState.value = AuthState.LoggedOut
                }
                override fun onFailure(error: AuthenticationException) {
                    app.credentialsManager.clearCredentials()
                    _authState.value = AuthState.LoggedOut
                }
            })
    }

    private fun fetchUserProfile(accessToken: String) {
        AuthenticationAPIClient(app.auth0)
            .userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onSuccess(result: UserProfile) {
                    _authState.value = AuthState.LoggedIn(result)
                }
                override fun onFailure(error: AuthenticationException) {
                    _authState.value = AuthState.LoggedOut
                }
            })
    }
}
