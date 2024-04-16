package src.model;

public enum GameState {

	START_TITLE, MAIN_MENU, LOAD_GAME, PLAYING, SELECT_AVATAR, OPTIONS, COMMAND_EXPLAINATION, QUIT,
	TRANSITION_STATE, PAUSE, DIALOGUE, BOSS_CUTSCENE, GAME_OVER, END_GAME, TRANSITION_AFTER_GAME_OVER;

	public static GameState actualState = START_TITLE;

	public static int difficulty = 0;

	//serve per quando abbiamo iniziato il gioco e torniamo alla home, per non avere l'ozione "iscriviti" ma solo quella "riprendi"
	public static boolean playStateInStandBy = false;

}


