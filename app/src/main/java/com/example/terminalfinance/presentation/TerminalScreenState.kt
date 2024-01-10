package com.example.terminalfinance.presentation

import com.example.terminalfinance.data.Bar

sealed class TerminalScreenState {

    object Initial: TerminalScreenState()

    object Loading: TerminalScreenState()

    data class Content(val barList: List<Bar>, val timeFrame: TimeFrame): TerminalScreenState()
}
