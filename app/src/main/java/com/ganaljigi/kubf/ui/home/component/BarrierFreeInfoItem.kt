package com.ganaljigi.kubf.ui.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Black
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun BarrierFreeInfoItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    visible: Boolean = false
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White.copy(alpha = 0.8f))
                .clip(RoundedCornerShape(20.dp))
                .noRippleClickable(onClick)
        ) {
            Box(
                modifier = modifier
                    .matchParentSize()
                    .blur(8.dp, BlurredEdgeTreatment.Unbounded)
            )
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = KUBFAndroidTheme.typography.semiBold14.copy(
                            color = MainGreen,
                            lineHeight = 25.sp
                        ).toSpanStyle()
                    ) {
                        append("배리어 프리(barrier-free)")
                    }
                    withStyle(
                        style = KUBFAndroidTheme.typography.regular14.copy(
                            color = Black,
                            lineHeight = 25.sp
                        ).toSpanStyle()
                    ) {
                        append(
                            "는 장애인 및 고령자, 임산부 등의 사회적 약자들의 사회 생활에 지장이 " +
                                    "되는 물리적인 장애물이나 심리적인 장벽을 없애기 위해 실시하는 운동 및 " +
                                    "시책을 말합니다. 일반적으로 장애인의 시설 이용에 장애가 되는 장벽을 없애는 " +
                                    "뜻으로 사용되고 있습니다."
                        )
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BarrierFreeInfoItemPreview() {
    BarrierFreeInfoItem(
        visible = true,
        onClick = { /* Do nothing */ }
    )
}