package com.mangata.core_ui.util

import android.widget.EditText
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


sealed class UiEvent {
    data class SnackbarEvent(val uiText: String, val uiActionLabel: String? = null) : UiEvent()
}