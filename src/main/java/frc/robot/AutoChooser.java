package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class sets up the chooser for the smart dashboard to select the goal
 * position
 * 
 */
public class AutoChooser {

	// Create chooser
	SendableChooser<String> autoChooser = new SendableChooser<>();

	public AutoChooser() {
		// Add options
		autoChooser.addDefault("Forward", "f");
		autoChooser.addObject("Left", "l");
		autoChooser.addObject("Right", "r");
		autoChooser.addObject("Back", "b");
	}

	/**
	 * Returns the chooser to output it to the smart dashboard
	 * 
	 * @return sendable chooser object
	 */
	public SendableChooser<String> getChooser() {
		return autoChooser;
	}

	/**
	 * Returns the currently selected option
	 * 
	 * @return string chosen from the dashboard
	 */
	public String getSelected() {
		return autoChooser.getSelected();
	}
}