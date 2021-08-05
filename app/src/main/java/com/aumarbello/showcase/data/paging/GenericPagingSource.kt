package com.aumarbello.showcase.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aumarbello.showcase.data.models.PaginatedResponse
import com.aumarbello.showcase.data.models.Pagination

class GenericPagingSource <T : Any, A> (
    private val argument: A,
    private val service: suspend (Int, A) -> PaginatedResponse<T>
): PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1
            val response = service(nextPage, argument)

            LoadResult.Page(
                response.items,
                prevKey = null,
                nextKey = getNextPage(response.pagination)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getNextPage(pagination: Pagination): Int? {
        val possibleTotalSoFar = pagination.currentPage * pagination.pageSize

        return if (possibleTotalSoFar < pagination.total)
            pagination.currentPage.inc()
        else
            null
    }
}