package com.example.chatting_video2.model

class ChatNewModel(val myUid : String, val yourUid : String, val message : String, val time : Long, val who : String){

    constructor() : this("", "", "", 0, "")

}
