package com.ganaljigi.kubf.ui.helper.component

import org.jsoup.Jsoup
import com.ganaljigi.kubf.ui.helper.viewmodel.Notice


fun fetchNotices(): List<Notice> {
    val doc = Jsoup .connect("https://www.konkuk.ac.kr/csd/15224/subview.do").get()
    val rows = doc.select(".bbsList > tbody > tr")
    return rows.take(3).map {
        val title = it.select("td.td-subject > a").text()
        val date = it.select("td.td-date").text()
        val url = "https://www.konkuk.ac.kr" + it.select("td.td-subject > a").attr("href")
        Notice(title, date, url)
    }
}
