package com.example.smacc

data class FAQData(val faq_list_data: List<FAQItem>) {
    data class FAQItem(val pertanyaan: String, val jawaban: String)
}