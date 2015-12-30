/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexerver;

class StatusMessage {

        public static final String CLIENT_DISCONNECT = "CLIENT::Disconnect";
        public static final String CLIENT_READY = "CLIENT::Ready";
        public static final String CLIENT_AI = "CLIENT::AI";
        public static final String CLIENT_PVP = "CLIENT::PVP";
        public static final String SERVER_WIN_1 = "SERVER::Winner::Player::1";
        public static final String SERVER_WIN_2 = "SERVER::Winner::Player::2";
        public static final String SERVER_WIN_TIE = "SERVER::Winner::No-one";
        public static final String SERVER_INVALID_DATA = "SERVER::Invalid data received::Re-send.";
        public static final String SERVER_GAMESTATE = "SERVER::Gamestate::";
        public static final String SERVER_START_GAME = "SERVER::Game starts.";
        public static final String SERVER_TURNED = "SERVER::Turned::";
        public static final String SERVER_ON_TURN_1 = "SERVER::Player on turn::1";
        public static final String SERVER_ON_TURN_2 = "SERVER::Player on turn::2";
        public static final String SERVER_CONN_ACCEPT = "SERVER::Connection accepted::You are Player::";
        public static final String SERVER_WAITING_P2 = "SERVER::Waiting for Player::2";
        public static final String SERVER_START_AI = "SERVER::Starting::AI";
        public static final String SERVER_INVALID_REPLY = "SERVER::Invalid reply::Disconnecting.";
		public static final String SERVER_BUSY = "SERVER::Busy";
}