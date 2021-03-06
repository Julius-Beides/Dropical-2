package at.dropical.shared.communication.local;
// Created by julian on 24.06.18.

import at.dropical.shared.GameState;
import at.dropical.shared.PlayerAction;
import at.dropical.shared.communication.ToServerMagic;

import java.util.LinkedList;
import java.util.List;

/**
 * Makes and communication with a local server.
 * The data is buffered between calls of send...() and get...()
 * and only changes with updateServer().
 *
 * fixme clear internal states every cycle
 */
public class LocalToServerMagic implements ToServerMagic {

    private LocalToClientMagic serverEnd;
    /* List must be cleared every cycle. */
    private List<PlayerAction> actionList = new LinkedList<>();

    public LocalToServerMagic(LocalServer localServer) {
        serverEnd = new LocalToClientMagic(this, localServer);
    }

    /** Package private. Read in LocalToServerMagic. */
    List<PlayerAction> getPlayerActions() {
        return actionList;
    }
    String customDataToServer = "";

    /* Public Methods for client. */

    @Override
    public void sendAction(PlayerAction action) {
        actionList.add(action);
    }

    @Override
    public void updateServer() {
        serverEnd.updateServer();
        actionList.clear();
    }

    @Override
    public byte[][] getArena() {
        return serverEnd.arena;
    }

    @Override
    public byte[][] getTetromino() {
        return serverEnd.tetromino;
    }

    @Override
    public int getTetrX() {
        return serverEnd.x;
    }

    @Override
    public int getTetrY() {
        return serverEnd.y;
    }

    @Override
    public byte[][] getNextTetromino() {
        return serverEnd.nextTetromino;
    }

    @Override
    public GameState getGameState() {
        return serverEnd.gameState;
    }

    @Override
    public void close() {
        serverEnd.close();
    }

    @Override
    public void sendCustomData(String string) {
        customDataToServer = string;
    }

    @Override
    public String getCustomData() {
        return serverEnd.customDataToClient;
    }
}
