package hoods.com.newsy.utils

import android.graphics.ColorSpace.Model
import androidx.paging.PagingSource
import kotlinx.coroutines.test.runTest

internal fun <PaginationKey : Any, Model : Any> PagingSource<PaginationKey, Model>.getTestData(
    pageSize: Int = 10
): List<Model> {
    val data = mutableListOf<Model>()
    runTest {
        val loadResult = load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )
        when (loadResult) {
            is PagingSource.LoadResult.Error -> throw loadResult.throwable
            is PagingSource.LoadResult.Page -> data.addAll(loadResult.data)
            else -> {}
        }
    }
    return data
}