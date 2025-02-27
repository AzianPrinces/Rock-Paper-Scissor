package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        //Implement better AI here...
        // If this is the first round, there's no history yet, so just pick Rock
        if (results.isEmpty()){
            return Move.Rock;
        }

        // Get the last round result
        Result lastResult = results.get(results.size()-1);

        // We need to figure out what move the HUMAN used in the last round
        // Because the lastResult has a "winner" and a "loser", we need to see
        // which side the human was on (winner or loser).
        // Alternatively, if it's a tie, the "winner" is the human, but the resultType is Tie.

        Move humanLastMove;
        // Check if the winner is the human
        if(lastResult.getWinnerPlayer().getPlayerType() == PlayerType.Human){
            // Then the human's move is the winnerMove
            humanLastMove = lastResult.getWinnerMove();
        }else {
            // Otherwise, the human's move is the loserMove
            // (In case of a tie, ironically the "loser" is the AI, so the "winner" was the human.
            //  But let's keep it simple—this logic still works out.)
            humanLastMove = lastResult.getLoserMove();
        }

        //Pick the move that beats the humans last move
        switch (humanLastMove){
            case Rock:
                return Move.Paper;
                case Paper:
                    return Move.Scissor;
                    case Scissor:
                        return Move.Rock;
                        default:
                            return Move.Rock;
        }



    }
}
