package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles
import com.devhjs.oilmap.presentation.home.HomeAction
import com.devhjs.oilmap.presentation.home.HomeState

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    state: HomeState = HomeState(),
    onAction: (HomeAction) -> Unit= {},
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

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
                text = "OilMap",
                style = AppTextStyles.headlineLarge,
                color = Color(0xFF0A0A0A)
            )
            Icon(
                painter = painterResource(R.drawable.star_filled),
                contentDescription = "즐겨찾기",
                tint = AppColors.FavoriteIcon,
                modifier = Modifier.clickable {
                    onAction(HomeAction.OnFavoriteClick)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FuelTypeButton(
                text = "휘발유",
                isSelected = state.selectedResourceType == "휘발유",
                icon = R.drawable.gasoline,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onAction(HomeAction.OnResourceTypeSelected("휘발유")) }
            )
            FuelTypeButton(
                text = "경유",
                isSelected = state.selectedResourceType == "경유",
                icon = R.drawable.diesel,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onAction(HomeAction.OnResourceTypeSelected("경유")) }
            )
            FuelTypeButton(
                text = "LPG",
                isSelected = state.selectedResourceType == "LPG",
                icon = R.drawable.lpg,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onAction(HomeAction.OnResourceTypeSelected("LPG")) }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "총 ",
                    style = AppTextStyles.listCountNormal,
                    color = AppColors.Gray700
                )
                Text(
                    text = "${state.totalCount}개",
                    style = AppTextStyles.listCountBold,
                    color = AppColors.Gray900
                )
                Text(
                    text = " 주유소",
                    style = AppTextStyles.listCountNormal,
                    color = AppColors.Gray700
                )
            }

            Box {
                Box(
                    modifier = Modifier
                        .width(92.dp)
                        .height(36.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(1.dp, AppColors.Border, RoundedCornerShape(10.dp))
                        .clickable { isDropdownExpanded = true },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.selectedSortOption,
                            style = AppTextStyles.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = AppColors.Gray800
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Drop Down",
                            tint = AppColors.Gray600,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("가격순", style = AppTextStyles.bodySmall, color = AppColors.Gray800) },
                        onClick = {
                            onAction(HomeAction.OnSortOptionSelected("가격순"))
                            isDropdownExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("거리순", style = AppTextStyles.bodySmall, color = AppColors.Gray800) },
                        onClick = {
                            onAction(HomeAction.OnSortOptionSelected("거리순"))
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeHeaderPreview() {
    HomeHeader()
}