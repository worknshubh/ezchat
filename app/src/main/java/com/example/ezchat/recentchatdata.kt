package com.example.ezchat

import com.google.firebase.Timestamp

data class recentchatdata(
    var username:String = "",
    var lastmessage:String = "",
    var timestamp: Timestamp?=null
)
