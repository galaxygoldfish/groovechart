package com.groovechart.app.android.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.consts.PageNavigationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.model.User
import com.groovechart.app.networking.SpotifyNetworkService
import com.tencent.mmkv.MMKV

class HomeViewModel : ViewModel() {

    var currentUser: User? by mutableStateOf(null)
    var showAccountDialog by mutableStateOf(false)
    var currentPage by mutableStateOf(PageNavigationKey.Home)

    suspend fun fetch() {
        val mmkv = MMKV.defaultMMKV()
        SpotifyNetworkService().fetchUserDetails(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                Log.e("s", "success, ${it.display_name}")
                currentUser = it
            }, { Log.e("D", "failure") }
        )
    }
}