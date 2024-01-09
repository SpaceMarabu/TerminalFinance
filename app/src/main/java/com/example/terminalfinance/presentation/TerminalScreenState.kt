package com.example.terminalfinance.presentation

import com.example.terminalfinance.data.Bar

sealed class TerminalScreenState {

    object Initial: TerminalScreenState()

    data class Content(val barList: List<Bar>): TerminalScreenState()
}
