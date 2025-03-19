package com.example.ezchat

import com.google.firebase.Timestamp

data class messages(
    var messagetext:String = "",
    var senderuid:String = "",
    var timestamp: Timestamp ?= null
)
