package com.inhouse.pocboxandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.inhouse.pocboxandroid.topiclist.TopicListViewModel
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import org.mockito.*

/**
 * Source: https://jeroenmols.com/blog/2019/01/17/livedatajunit5/
 * https://izziswift.com/kotlin-unit-testing-room-and-livedata/
 */
@ExtendWith(InstantExecutorExtension::class)
class TopicListViewModelUnitTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Int?>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Int?>

    @Mock
    private lateinit var viewModel: TopicListViewModel

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    fun `Test navigationIdx`() {
        viewModel = TopicListViewModel()
        viewModel.navigationIdx.observeForever(observer)
        viewModel.updateNavigationIdx(1)

        Mockito.verify(observer, Mockito.times(1)).onChanged(argumentCaptor.capture())

        val values: MutableList<Int?> = argumentCaptor.allValues
        print(values)
        Assertions.assertEquals(1, values[0])
    }
}