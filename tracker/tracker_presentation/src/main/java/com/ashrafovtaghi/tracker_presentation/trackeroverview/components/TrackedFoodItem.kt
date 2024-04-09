package com.ashrafovtaghi.tracker_presentation.trackeroverview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ashrafovtaghi.core.R
import com.ashrafovtaghi.core_ui.LocalSpacing
import com.ashrafovtaghi.tracker_domain.model.TrackedFood
import com.ashrafovtaghi.tracker_presentation.composables.NutrientInfo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .clip(
                RoundedCornerShape(5.dp)
            )
            .padding(spacing.spaceExtraSmall)
            .shadow(1.dp, shape = RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.surface)
            .padding(end = spacing.spaceMedium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberImagePainter(data = trackedFood.imageUrl, builder = {
                crossfade(true)
                error(R.drawable.ic_burger)
                fallback(R.drawable.ic_burger)
            }),
            contentDescription = trackedFood.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
        )

        Spacer(modifier = Modifier.width(spacing.spaceMedium))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = trackedFood.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = stringResource(
                    id = R.string.nutrient_info,
                    trackedFood.amount,
                    trackedFood.calories
                ),
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        onDeleteClick.invoke()
                    }
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = trackedFood.carbs,
                    amountSize = 16.sp,
                    unitTextSize = 12.sp,
                    unit = stringResource(
                        id = R.string.grams
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = trackedFood.protein,
                    amountSize = 16.sp,
                    unitTextSize = 12.sp,
                    unit = stringResource(
                        id = R.string.grams
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = trackedFood.fat,
                    amountSize = 16.sp,
                    unitTextSize = 12.sp,
                    unit = stringResource(
                        id = R.string.grams
                    )
                )
            }
        }
    }
}
