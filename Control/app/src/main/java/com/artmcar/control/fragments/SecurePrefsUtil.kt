package com.artmcar.control.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecurePrefsUtil {

    fun getSecurePrefs(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun isMainFragmentLocked(context: Context): Boolean {
        val prefs = getSecurePrefs(context)
        val pin = prefs.getString("pin", null)
        val locked = prefs.getBoolean("locked", false)
        return !pin.isNullOrEmpty() && locked
    }

    fun setLocked(context: Context, locked: Boolean) {
        getSecurePrefs(context).edit().putBoolean("locked", locked).apply()
    }

    fun setLockedNextLaunch(context: Context, locked: Boolean) {
        getSecurePrefs(context).edit().putBoolean("locked_next_launch", locked).apply()
    }

    fun applyPendingLock(context: Context) {
        val prefs = getSecurePrefs(context)
        val pin = prefs.getString("pin", null)
        val lockedNextLaunch = prefs.getBoolean("locked_next_launch", false)

        if (!pin.isNullOrEmpty() && lockedNextLaunch) {
            prefs.edit()
                .putBoolean("locked", true)
                .putBoolean("locked_next_launch", false)
                .apply()
        }
    }
}