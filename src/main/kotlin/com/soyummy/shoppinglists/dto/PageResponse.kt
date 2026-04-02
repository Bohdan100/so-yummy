package com.soyummy.shoppinglists.dto

import org.springframework.data.domain.Page

data class PageResponse<T : Any>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean,
    val first: Boolean,
    val empty: Boolean
) {
    companion object {
        fun <T : Any> from(page: Page<T>): PageResponse<T> {
            return PageResponse(
                content = page.content,
                pageNumber = page.number,
                pageSize = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                last = page.isLast,
                first = page.isFirst,
                empty = page.isEmpty
            )
        }
    }
}
