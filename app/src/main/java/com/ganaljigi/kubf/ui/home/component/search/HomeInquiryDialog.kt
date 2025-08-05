package com.ganaljigi.kubf.ui.home.component.search

import android.R.attr.textStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.conditionalModifier
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeInquiryDialog(
    modifier: Modifier = Modifier,
    inquiryField: TextFieldValue,
    onInquiryFieldChange: (TextFieldValue) -> Unit = {},
    onSubmit: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "추가로 보고 싶은 내용을 알려주세요.",
                style = KUBFAndroidTheme.typography.semiBold18,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            InquiryTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                inquiryField = inquiryField,
                onInquiryFieldChange = onInquiryFieldChange,
                interactionSource = interactionSource,
                isFocused = isFocused
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_inquiry_warning),
                    contentDescription = "Warning Icon",
                    tint = Color.Unspecified,
                )
                Text(
                    text = "모든 문의는 신중히 검토되며, 반복적이거나\n" +
                            "부적절한 내용은 제외될 수 있습니다.",
                    style = KUBFAndroidTheme.typography.medium15.copy(color = Gray2),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MainGreen)
                    .clickable(onClick = onSubmit),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "제출하기",
                    style = KUBFAndroidTheme.typography.regular14.copy(color = Color.White)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InquiryTextField(
    modifier: Modifier = Modifier,
    inquiryField: TextFieldValue,
    onInquiryFieldChange: (TextFieldValue) -> Unit = {},
    interactionSource: MutableInteractionSource,
    isFocused: Boolean,
) {
    Box(
        modifier = modifier
            .conditionalModifier(
                condition = isFocused,
                modifierIfTrue = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = MainGreen
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                modifierIfFalse = Modifier.border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = Gray2
                )
            )
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            modifier = Modifier.align(Alignment.TopStart),
            value = inquiryField,
            onValueChange = {
                if (it.text.length <= 100) {
                    onInquiryFieldChange(it)
                } else {
                    onInquiryFieldChange(it.copy(text = it.text.take(100)))
                }
            },
            cursorBrush = SolidColor(Gray4), // Cursor color
            textStyle = KUBFAndroidTheme.typography.medium15.copy(),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                if (inquiryField.text.isEmpty()) {
                    Text(
                        text = "예: OO 편의시설 정보가 없어요, 장애인 화장실 위치도 알려주세요",
                        style = KUBFAndroidTheme.typography.medium15.copy(
                            color = Gray2,
                        )
                    )
                }
                innerTextField()
            }
        )

        Row(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = inquiryField.text.length.toString(),
                style = KUBFAndroidTheme.typography.medium16.copy(
                    color = MainGreen,
                )
            )
            Text(
                text = "/",
                style = KUBFAndroidTheme.typography.medium16.copy(
                    color = Gray3,
                )
            )
            Text(
                text = "100",
                style = KUBFAndroidTheme.typography.medium16.copy(
                    color = Gray3,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeInquiryDialogPreview() {
    HomeInquiryDialog(
        inquiryField = TextFieldValue("새로 생긴 이마트24 정보 추가해주세요. 새로 생긴 이마트24"),
        onInquiryFieldChange = {},
        onSubmit = {},
        onDismissRequest = {}
    )
}