package hoods.com.newsy.features.presentation

import androidx.lifecycle.ViewModel
import hoods.com.newsy.features.components.headline.domain.usecases.GetHeadlineUseCase
import javax.inject.Inject

class HeadlineViewModel @Inject constructor(private val getHeadlineUseCase: GetHeadlineUseCase) : ViewModel() {


}
