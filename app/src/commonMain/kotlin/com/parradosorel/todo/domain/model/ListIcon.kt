package com.parradosorel.todo.domain.model

/**
 * The icons a list can have, in the order the picker shows them. Saved by name, so never rename
 * an entry; an unknown name reads as [TODO]. The picture for each lives only in the UI.
 */
enum class ListIcon {
    TODO, GROCERIES, SHOPPING, GIFTS, CHRISTMAS, HALLOWEEN,
    EASTER, BIRTHDAY, PARTY, WEDDING, VALENTINES, SCHOOL,
    VACATION, TRAVEL, CAMPING, CAR, DOG, CAT,
    BABY, KIDS, HOME, CLEANING, LAUNDRY, REPAIRS,
    GARDEN, MOVING, COOKING, BAKING, BBQ, RESTAURANTS,
    COFFEE, DRINKS, HEALTH, FITNESS, BEAUTY, CLOTHES,
    WORK, MONEY, BILLS, CALLS, BOOKS, MOVIES,
    MUSIC, GAMES, IDEAS, SUMMER, WINTER, FAVORITES;

    companion object {
        fun fromName(name: String): ListIcon = entries.firstOrNull { it.name == name } ?: TODO
    }
}
