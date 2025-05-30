package com.dvidal.ui_herolist.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dvidal.components.R
import com.dvidal.constants.Constants
import com.dvidal.hero_domain.Hero
import com.dvidal.ui_herolist.ui.test.TAG_HERO_NAME
import com.dvidal.ui_herolist.ui.test.TAG_HERO_PRIMARY_ATTRIBUTE
import kotlin.math.roundToInt

@Composable
fun HeroListItem(
    hero: Hero,
    imageBuilder: ImageRequest.Builder,
    onSelectHero: (Int) -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colors.surface)
            .clickable {
                onSelectHero(hero.id)
            }
        ,
        elevation = 8.dp
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ){

            AsyncImage(
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .background(Color.LightGray),
                model = imageBuilder
                    .data(Constants.HERO_IMAGE)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error_image),
                placeholder = painterResource(
                    if (isSystemInDarkTheme())
                        R.drawable.black_background
                    else R.drawable.white_background
                ),
                contentDescription = hero.localizedName,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f) // fill 80% of remaining width
                    .padding(start = 12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .testTag(TAG_HERO_NAME)
                    ,
                    text = hero.localizedName,
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .testTag(TAG_HERO_PRIMARY_ATTRIBUTE)
                    ,
                    text = hero.primaryAttribute.uiValue,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Fill the rest of the width (100% - 80% = 20%)
                    .padding(end = 12.dp)
                ,
                horizontalAlignment = Alignment.End
            ) {
                // Using remember in list item does not behave correctly?
//                val proWR: Int = remember{round(hero.proWins.toDouble() / hero.proPick.toDouble() * 100).toInt()}
                val proWR: Int = (hero.proWins.toDouble() / hero.proPick.toDouble() * 100).roundToInt()

                Text(
                    text = "${proWR}%",
                    style = MaterialTheme.typography.caption,
                    color = if(proWR > 50) Color(0xFF009a34) else MaterialTheme.colors.error,
                )
            }
        }
    }
}