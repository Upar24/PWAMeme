package com.example.pwameme.ui.screens.creatememe

import androidx.lifecycle.ViewModel
import com.example.pwameme.repository.MemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateMemeViewModel @Inject constructor(
    private val repository: MemeRepository
): ViewModel(){
}