package frc.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.subsystems.DriveTrain;

public class DriveToDistance extends Command {
  double distance;
  DriveTrain driveTrain;
  boolean end;

  public DriveToDistance() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

// Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveTrain.reset();
  }

  private double getDistance() {
    return Math.abs((Robot.driveTrain.getLeftDistanceInFeet()) + (Robot.driveTrain.getRightDistanceInFeet()) / 2);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    System.out.println("executed");
    if (Math.abs(getDistance()) >= 5){
      Robot.driveTrain.joystickDrive(0, 0);
   } else{
     Robot.driveTrain.joystickDrive(0.5, 0);
   }


  /* 
   double leftPosition = Robot.driveTrain.leftMaster.getSelectedSensorPosition() * Robot.driveTrain.distancePerPulse;
   double rightPosition = Robot.driveTrain.rightMaster.getSelectedSensorPosition() * Robot.driveTrain.distancePerPulse;
   double distance = (leftPosition + rightPosition) / 2;
  
   if (distance <  -5) {
    Robot.driveTrain.joystickDrive(0.5, 0);
   } else {
    Robot.driveTrain.joystickDrive(0, 0);
    }
    */
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
//    System.out.println("isFinished");
//    return Robot.driveTrain.getLeftDistanceInFeet() >= distance;

    if(Robot.driveTrain.getLeftDistanceInFeet() < 5){
      end = false;
    } else if(Robot.driveTrain.getLeftDistanceInFeet() >= 5){
      end = true;
    }
    System.out.println("end is " + end);
    return end;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("end");
    Robot.driveTrain.joystickDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.driveTrain.joystickDrive(0, 0);
  }
}
