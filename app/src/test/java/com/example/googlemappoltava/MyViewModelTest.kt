package com.example.googlemappoltava

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MyViewModelTest {

    @Mock
    private lateinit var myRepository: MyRepository

    @Mock
    private lateinit var placesObserver: Observer<List<Results>>

    private lateinit var viewModel: MyViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MyViewModel(myRepository)
    }

    @Test
    fun `test loadPlaces`() {
        val mockPlacesList = listOf<Results>(mock())
        runBlocking {
            Mockito.`when`(myRepository.getNearbyPlaces()).thenReturn(mockPlacesList)

            viewModel.placesList.observeForever(placesObserver)

            viewModel.loadPlaces()

            Mockito.verify(placesObserver).onChanged(mockPlacesList)
            viewModel.placesList.removeObserver(placesObserver)
        }
    }
}