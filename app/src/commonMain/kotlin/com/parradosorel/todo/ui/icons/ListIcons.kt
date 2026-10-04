package com.parradosorel.todo.ui.icons

import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.resources.Res
import com.parradosorel.todo.resources.icon_todo
import com.parradosorel.todo.resources.icon_groceries
import com.parradosorel.todo.resources.icon_shopping
import com.parradosorel.todo.resources.icon_gifts
import com.parradosorel.todo.resources.icon_christmas
import com.parradosorel.todo.resources.icon_halloween
import com.parradosorel.todo.resources.icon_easter
import com.parradosorel.todo.resources.icon_birthday
import com.parradosorel.todo.resources.icon_party
import com.parradosorel.todo.resources.icon_wedding
import com.parradosorel.todo.resources.icon_valentines
import com.parradosorel.todo.resources.icon_school
import com.parradosorel.todo.resources.icon_vacation
import com.parradosorel.todo.resources.icon_travel
import com.parradosorel.todo.resources.icon_camping
import com.parradosorel.todo.resources.icon_car
import com.parradosorel.todo.resources.icon_dog
import com.parradosorel.todo.resources.icon_cat
import com.parradosorel.todo.resources.icon_baby
import com.parradosorel.todo.resources.icon_kids
import com.parradosorel.todo.resources.icon_home
import com.parradosorel.todo.resources.icon_cleaning
import com.parradosorel.todo.resources.icon_laundry
import com.parradosorel.todo.resources.icon_repairs
import com.parradosorel.todo.resources.icon_garden
import com.parradosorel.todo.resources.icon_moving
import com.parradosorel.todo.resources.icon_cooking
import com.parradosorel.todo.resources.icon_baking
import com.parradosorel.todo.resources.icon_bbq
import com.parradosorel.todo.resources.icon_restaurants
import com.parradosorel.todo.resources.icon_coffee
import com.parradosorel.todo.resources.icon_drinks
import com.parradosorel.todo.resources.icon_health
import com.parradosorel.todo.resources.icon_fitness
import com.parradosorel.todo.resources.icon_beauty
import com.parradosorel.todo.resources.icon_clothes
import com.parradosorel.todo.resources.icon_work
import com.parradosorel.todo.resources.icon_money
import com.parradosorel.todo.resources.icon_bills
import com.parradosorel.todo.resources.icon_calls
import com.parradosorel.todo.resources.icon_books
import com.parradosorel.todo.resources.icon_movies
import com.parradosorel.todo.resources.icon_music
import com.parradosorel.todo.resources.icon_games
import com.parradosorel.todo.resources.icon_ideas
import com.parradosorel.todo.resources.icon_summer
import com.parradosorel.todo.resources.icon_winter
import com.parradosorel.todo.resources.icon_favorites
import org.jetbrains.compose.resources.StringResource

/** The emoji drawn for each [ListIcon]. */
val ListIcon.emoji: String
    get() = when (this) {
        ListIcon.TODO -> "✅"
        ListIcon.GROCERIES -> "🛒"
        ListIcon.SHOPPING -> "🛍️"
        ListIcon.GIFTS -> "🎁"
        ListIcon.CHRISTMAS -> "🎄"
        ListIcon.HALLOWEEN -> "🎃"
        ListIcon.EASTER -> "🐣"
        ListIcon.BIRTHDAY -> "🎂"
        ListIcon.PARTY -> "🎉"
        ListIcon.WEDDING -> "💍"
        ListIcon.VALENTINES -> "💝"
        ListIcon.SCHOOL -> "🎒"
        ListIcon.VACATION -> "🏖️"
        ListIcon.TRAVEL -> "✈️"
        ListIcon.CAMPING -> "⛺"
        ListIcon.CAR -> "🚗"
        ListIcon.DOG -> "🐶"
        ListIcon.CAT -> "🐱"
        ListIcon.BABY -> "🍼"
        ListIcon.KIDS -> "🧸"
        ListIcon.HOME -> "🏠"
        ListIcon.CLEANING -> "🧹"
        ListIcon.LAUNDRY -> "🧺"
        ListIcon.REPAIRS -> "🛠️"
        ListIcon.GARDEN -> "🌱"
        ListIcon.MOVING -> "📦"
        ListIcon.COOKING -> "🍳"
        ListIcon.BAKING -> "🧁"
        ListIcon.BBQ -> "🍖"
        ListIcon.RESTAURANTS -> "🍽️"
        ListIcon.COFFEE -> "☕"
        ListIcon.DRINKS -> "🍷"
        ListIcon.HEALTH -> "💊"
        ListIcon.FITNESS -> "💪"
        ListIcon.BEAUTY -> "💄"
        ListIcon.CLOTHES -> "👕"
        ListIcon.WORK -> "💼"
        ListIcon.MONEY -> "💰"
        ListIcon.BILLS -> "🧾"
        ListIcon.CALLS -> "📞"
        ListIcon.BOOKS -> "📚"
        ListIcon.MOVIES -> "🎬"
        ListIcon.MUSIC -> "🎵"
        ListIcon.GAMES -> "🎮"
        ListIcon.IDEAS -> "💡"
        ListIcon.SUMMER -> "☀️"
        ListIcon.WINTER -> "❄️"
        ListIcon.FAVORITES -> "⭐"
    }

/** What the icon shows, for screen readers. */
val ListIcon.label: StringResource
    get() = when (this) {
        ListIcon.TODO -> Res.string.icon_todo
        ListIcon.GROCERIES -> Res.string.icon_groceries
        ListIcon.SHOPPING -> Res.string.icon_shopping
        ListIcon.GIFTS -> Res.string.icon_gifts
        ListIcon.CHRISTMAS -> Res.string.icon_christmas
        ListIcon.HALLOWEEN -> Res.string.icon_halloween
        ListIcon.EASTER -> Res.string.icon_easter
        ListIcon.BIRTHDAY -> Res.string.icon_birthday
        ListIcon.PARTY -> Res.string.icon_party
        ListIcon.WEDDING -> Res.string.icon_wedding
        ListIcon.VALENTINES -> Res.string.icon_valentines
        ListIcon.SCHOOL -> Res.string.icon_school
        ListIcon.VACATION -> Res.string.icon_vacation
        ListIcon.TRAVEL -> Res.string.icon_travel
        ListIcon.CAMPING -> Res.string.icon_camping
        ListIcon.CAR -> Res.string.icon_car
        ListIcon.DOG -> Res.string.icon_dog
        ListIcon.CAT -> Res.string.icon_cat
        ListIcon.BABY -> Res.string.icon_baby
        ListIcon.KIDS -> Res.string.icon_kids
        ListIcon.HOME -> Res.string.icon_home
        ListIcon.CLEANING -> Res.string.icon_cleaning
        ListIcon.LAUNDRY -> Res.string.icon_laundry
        ListIcon.REPAIRS -> Res.string.icon_repairs
        ListIcon.GARDEN -> Res.string.icon_garden
        ListIcon.MOVING -> Res.string.icon_moving
        ListIcon.COOKING -> Res.string.icon_cooking
        ListIcon.BAKING -> Res.string.icon_baking
        ListIcon.BBQ -> Res.string.icon_bbq
        ListIcon.RESTAURANTS -> Res.string.icon_restaurants
        ListIcon.COFFEE -> Res.string.icon_coffee
        ListIcon.DRINKS -> Res.string.icon_drinks
        ListIcon.HEALTH -> Res.string.icon_health
        ListIcon.FITNESS -> Res.string.icon_fitness
        ListIcon.BEAUTY -> Res.string.icon_beauty
        ListIcon.CLOTHES -> Res.string.icon_clothes
        ListIcon.WORK -> Res.string.icon_work
        ListIcon.MONEY -> Res.string.icon_money
        ListIcon.BILLS -> Res.string.icon_bills
        ListIcon.CALLS -> Res.string.icon_calls
        ListIcon.BOOKS -> Res.string.icon_books
        ListIcon.MOVIES -> Res.string.icon_movies
        ListIcon.MUSIC -> Res.string.icon_music
        ListIcon.GAMES -> Res.string.icon_games
        ListIcon.IDEAS -> Res.string.icon_ideas
        ListIcon.SUMMER -> Res.string.icon_summer
        ListIcon.WINTER -> Res.string.icon_winter
        ListIcon.FAVORITES -> Res.string.icon_favorites
    }
