package com.js.playground.service.search

data class SearchRequest(val q: String, val location: String, val hl: String, val gl: String, val google_domain: String) {
    constructor(q: String) : this(q, "Seoul, Seoul, South Korea", "ko", "kp", "google.co.kr")
}