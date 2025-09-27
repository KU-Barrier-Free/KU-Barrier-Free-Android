package com.ganaljigi.kubf.ui.common.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ganaljigi.kubf.ui.theme.Black
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.LightGreen
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun PermissionDialog(
    context: Context,
    showRationaleDialog: Boolean = false,
    showOpenSettingsDialog: Boolean = false,
    onRetryClick: () -> Unit = { },
    onDismissRationaleDialog: () -> Unit = { },
    onDismissOpenAppSettingsDialog: () -> Unit = { },
) {
    if (showRationaleDialog) {
        PermissionRationaleDialog(
            onDismissRequest = onDismissRationaleDialog,
            title = "위치 권한을 허용해주세요!",
            content = "더 나은 서비스 이용을 위해 위치 권한을 허용해주세요.",
            onSettingsClick = {
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", context.packageName, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            },
            onRetryClick = onRetryClick,
        )
    }
    if (showOpenSettingsDialog) {
        OpenAppSettingsDialog(
            onDismissRequest = onDismissOpenAppSettingsDialog,
            title = "위치 권한이 거부되었어요",
            content = "앱 설정에서 위치 권한을 허용해주세요.",
            onSettingsClick = {
                onDismissOpenAppSettingsDialog()
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", context.packageName, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            },
        )
    }
}

@Composable
internal fun PermissionRationaleDialog(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
    onSettingsClick: () -> Unit = { },
    onRetryClick: () -> Unit = { },
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = KUBFAndroidTheme.typography.semiBold16,
                color = Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                style = KUBFAndroidTheme.typography.regular14,
                color = Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Button(
                    onClick = onSettingsClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray1,
                        contentColor = Black,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                ) {
                    Text(
                        text = "설정",
                        style = KUBFAndroidTheme.typography.semiBold14,
                        color = Black,
                    )
                }
                Button(
                    onClick = onRetryClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MainGreen),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen,
                        contentColor = Black,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                ) {
                    Text(
                        text = "재시도",
                        style = KUBFAndroidTheme.typography.semiBold14,
                        color = MainGreen,
                    )
                }
            }
        }
    }
}

@Composable
internal fun OpenAppSettingsDialog(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
    onSettingsClick: () -> Unit = { },
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = KUBFAndroidTheme.typography.semiBold16,
                color = Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                style = KUBFAndroidTheme.typography.regular14,
                color = Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Button(
                    onClick = onSettingsClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray1,
                        contentColor = Black,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                ) {
                    Text(
                        text = "설정",
                        style = KUBFAndroidTheme.typography.semiBold14,
                        color = Black,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun PermissionRationaleDialogPreview() {
    KUBFAndroidTheme {
        PermissionRationaleDialog(
            onDismissRequest = {},
            onSettingsClick = {},
            onRetryClick = {},
            title = "위치 권한을 허용해주세요!",
            content = "더 나은 서비스 이용을 위해 위치 권한을 허용해주세요.",
        )
    }
}

@Preview
@Composable
internal fun OpenAppSettingsDialogPreview() {
    KUBFAndroidTheme {
        OpenAppSettingsDialog(
            onDismissRequest = {},
            onSettingsClick = {},
            title = "위치 권한이 거부되었어요",
            content = "앱 설정에서 위치 권한을 허용해주세요.",
        )
    }
}