package src.model;

public enum GameState {

	START_TITLE, MAIN_MENU, LOAD_GAME, PLAYING, SELECT_AVATAR, OPTIONS, QUIT, TRANSITION_STATE, PAUSE, DIALOGUE, BOSS_CUTSCENE, GAME_OVER;

	public static GameState actualState = START_TITLE;

}


