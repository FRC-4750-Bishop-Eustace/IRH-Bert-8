package frc.auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class AutoCheckShooter extends InstantCommand {

  public double AutonWait;

  // Called once when the command executes
  @Override
  protected void initialize() {
    AutonWait = System.currentTimeMillis();
    Robot.noPIDShooter.runShooter();
    if((Robot.intestineTransport.IR4Count == 3) || ((System.currentTimeMillis() - AutonWait) >= 10000)){
      Robot.noPIDShooter.stopShooter();
    }
  }

}