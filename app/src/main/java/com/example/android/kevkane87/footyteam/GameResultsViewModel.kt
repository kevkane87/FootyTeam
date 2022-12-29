package com.example.android.kevkane87.footyteam

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.database.GameResultDatabase.Companion.getDatabase
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 50

class GameResultsViewModel (
    application: Application,
) : ViewModel()  {

    private val repository = Repository(getDatabase(application))

    /**
     * Stream of immutable states representative of the UI.
     */

    val items: Flow<PagingData<GameResult>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.gameResultPagingSource() }
    )
        .flow
        // cachedIn allows paging to remain active in the viewModel scope, so even if the UI
        // showing the paged data goes through lifecycle changes, pagination remains cached and
        // the UI does not have to start paging from the beginning when it resumes.
        .cachedIn(viewModelScope)
}