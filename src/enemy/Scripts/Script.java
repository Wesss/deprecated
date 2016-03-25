package enemy.scripts;

public interface Script {
	
	/**
	 * This method is called every frame that this script is active
	 * @param frame the number of frames that has past since this script was initiallized
	 */
	public void update(int frame);
}
