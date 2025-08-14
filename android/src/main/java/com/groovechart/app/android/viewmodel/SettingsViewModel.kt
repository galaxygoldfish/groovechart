package com.groovechart.app.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.R
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.consts.RearrangeHomeSection
import com.tencent.mmkv.MMKV

class SettingsViewModel : ViewModel() {

    val mmkv = MMKV.defaultMMKV()
    var arrangementOrder: List<String> by mutableStateOf(emptyList())
    var showEditRearrangeSectionDialog by mutableStateOf(false)
    var currentRearrangeSectionEditing by mutableIntStateOf(RearrangeHomeSection.TOP_GENRES)

    fun fetchArrangementOrder(context: Context) {
        val topTracks = context.getString(R.string.settings_homepage_rearrange_tracks)
        val topArtists = context.getString(R.string.settings_homepage_rearrange_artists)
        val topGenres = context.getString(R.string.settings_homepage_rearrange_genres)
        val orderDecoded = mmkv.decodeString(
            PreferenceKey.PREFERENCE_ARRANGEMENT_ORDER,
            listOf(topGenres, topTracks, topArtists).toString()
        )!!
        arrangementOrder = orderDecoded.substring(1, orderDecoded.length - 1).split(",")
    }

    fun saveArrangementPreferences() {
        mmkv.encode(PreferenceKey.PREFERENCE_ARRANGEMENT_ORDER, arrangementOrder.toString())
    }

}