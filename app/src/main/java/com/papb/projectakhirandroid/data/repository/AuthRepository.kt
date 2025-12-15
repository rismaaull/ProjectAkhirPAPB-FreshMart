package com.papb.projectakhirandroid.data.repository

import com.papb.projectakhirandroid.data.SupabaseClientProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val supabaseClient: SupabaseClient
) {

    private val authClient = supabaseClient.auth

    // Menggunakan sessionStatus dari Supabase untuk memantau perubahan status autentikasi secara real-time.
    // Ini menangani kasus login, logout, dan pemuatan sesi dari penyimpanan lokal (auto-login).
    val authState: Flow<Boolean> = authClient.sessionStatus.map { status ->
        status is SessionStatus.Authenticated
    }

    init {
        // PENTING: Supabase Auth perlu memuat session dari storage saat aplikasi dimulai
        // jika menggunakan Persistent Storage.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authClient.loadFromStorage()
            } catch (e: Exception) {
                // Ignore if load fails, user will just be logged out
            }
        }
    }

    /**
     * Melakukan registrasi user baru dengan email dan password.
     */
    suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            authClient.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Melakukan login dengan email dan password.
     */
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            authClient.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Melakukan logout.
     */
    suspend fun logout() {
        try {
            authClient.signOut()
        } catch (e: Exception) {
            // Jika gagal logout di server, kita tidak perlu melakukan apa-apa karena state lokal akan tetap update jika di-handle library
        }
    }

    /**
     * Memeriksa apakah ada user yang sedang login saat ini (Synchronous check).
     */
    fun isUserLoggedIn(): Boolean {
        // Kita cek session yang ada di memory saat ini
        return authClient.currentSessionOrNull() != null
    }
}
