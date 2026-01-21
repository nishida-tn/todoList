package com.thalesnishida.todo.presetention.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R
import com.thalesnishida.todo.presetention.ui.theme.TodoTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onSkipClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onComplete: () -> Unit = {}
) {
    data class OnboardingPage(
        val title: Int,
        val description: Int,
        val image: Int
    )

    val onboardingPages = listOf(
        OnboardingPage(
            R.string.onboarding_title_1,
            R.string.onboarding_description_1,
            R.drawable.ic_onboarding_1
        ),
        OnboardingPage(
            R.string.onboarding_title_2,
            R.string.onboarding_description_2,
            R.drawable.ic_onboarding_2
        ),
        OnboardingPage(
            R.string.onboarding_title_3,
            R.string.onboarding_description_3,
            R.drawable.ic_onboarding_3
        ),
    )

    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState
    ) { page ->
        val currentPageData = onboardingPages[page]
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.onboarding_skip),
                    color = Color.White.copy(alpha = 0.44f),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clickable { onSkipClick() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.ic_onboarding_1),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(42.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(onboardingPages.size) { iteration ->
                    val isSelected = pagerState.currentPage == iteration
                    val color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                    val width = if (isSelected) 24.dp else 12.dp

                    Box(
                        modifier = Modifier
                            .size(width = width, height = 4.dp)
                            .background(color, CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = stringResource(onboardingPages[page].title),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = stringResource(onboardingPages[page].description),
                color = Color.White.copy(alpha = 0.87f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 14.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.onboarding_back),
                    color = Color.White.copy(alpha = 0.44f),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable { onBackClick() }
                )

                val isLastPage = pagerState.currentPage == onboardingPages.lastIndex

                Button(
                    onClick = {
                        if (isLastPage) {
                            onComplete()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                ) {
                    Text(text = if (isLastPage) "GET STARTED" else "NEXT")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    TodoTheme {
        OnboardingScreen()
    }
}
