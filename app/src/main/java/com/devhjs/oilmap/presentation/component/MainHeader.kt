package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun MainHeader(
    modifier: Modifier = Modifier,
    selectedOilType: OilType,
    onOilTypeSelected: (OilType) -> Unit,
    onSettingsClick: (() -> Unit) = { },
    bottomContent: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "주유 어디",
                style = AppTextStyles.headlineLarge.copy(color = Color(0xFF0A0A0A)),
            )
            Icon(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Settings",
                tint = AppColors.Gray600,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsClick() }
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        // 유종 탭
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FuelTypeButton(
                text = OilType.GASOLINE.displayName,
                isSelected = selectedOilType == OilType.GASOLINE,
                icon = R.drawable.gasoline,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOilTypeSelected(OilType.GASOLINE) }
            )
            FuelTypeButton(
                text = OilType.DIESEL.displayName,
                isSelected = selectedOilType == OilType.DIESEL,
                icon = R.drawable.diesel,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOilTypeSelected(OilType.DIESEL) }
            )
            FuelTypeButton(
                text = OilType.LPG.displayName,
                isSelected = selectedOilType == OilType.LPG,
                icon = R.drawable.lpg,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOilTypeSelected(OilType.LPG) }
            )
        }

        bottomContent()
    }
}
