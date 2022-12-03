enum class OpponentState {
    A, B, C;
}

enum class PlayerState(val point: Int) {
    X(1), Y(2), Z(3);

}

enum class UpdatedPlayerState(val point: Int) {
    X(0), Y(3), Z(6);
}

fun OpponentState.calculate(playerState: PlayerState): Int {
    return when (this) {
        OpponentState.A -> when (playerState) {
            PlayerState.X -> 3
            PlayerState.Y -> 6
            PlayerState.Z -> 0
        }
        OpponentState.B -> when (playerState) {
            PlayerState.X -> 0
            PlayerState.Y -> 3
            PlayerState.Z -> 6
        }
        OpponentState.C -> when (playerState) {
            PlayerState.X -> 6
            PlayerState.Y -> 0
            PlayerState.Z -> 3
        }
    } + playerState.point
}

fun OpponentState.calculate(updatedPlayerState: UpdatedPlayerState): Int {
    return updatedPlayerState.point + when (this) {
        OpponentState.A -> when (updatedPlayerState) {
            UpdatedPlayerState.X -> PlayerState.Z
            UpdatedPlayerState.Y -> PlayerState.X
            UpdatedPlayerState.Z -> PlayerState.Y
        }
        OpponentState.B -> when (updatedPlayerState) {
            UpdatedPlayerState.X -> PlayerState.X
            UpdatedPlayerState.Y -> PlayerState.Y
            UpdatedPlayerState.Z -> PlayerState.Z
        }
        OpponentState.C -> when (updatedPlayerState) {
            UpdatedPlayerState.X -> PlayerState.Y
            UpdatedPlayerState.Y -> PlayerState.Z
            UpdatedPlayerState.Z -> PlayerState.X
        }
    }.point
}

fun main() {
    input("Day2").readLines().map { it.split(" ") }.sumOf { (a, b) ->
        OpponentState.valueOf(a).calculate(PlayerState.valueOf(b))
    }.also { println(it) }

    input("Day2").readLines().map { it.split(" ") }.sumOf { (a, b) ->
        OpponentState.valueOf(a).calculate(UpdatedPlayerState.valueOf(b))
    }.also { println(it) }
}