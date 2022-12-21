package co.africanwolf.hiddenwords.data.entity

import kotlinx.serialization.Serializable

enum class GameLevel(val levelName: String, val timeLimitInMillis: Long) {
    EASY("Easy", 300_000),
    MEDIUM("Medium", 270_000),
    HARD("Hard", 240_000),
    PRO("Pro", 180_000);
}

@Serializable

data class Game(
    var id: Int,
    var type: GameType,
    var color: String,
    var gameLevel: GameLevel = GameLevel.EASY,
    var levels: Int = 100
)

@Serializable
data class Player(
    var id: String,
    var name: String,
    var curGameId: Int = -1,
    var gameProgress: List<GameProgress>
)

@Serializable
data class GameProgress(
    var gameId: Int,
    var curLevels: Int
)
