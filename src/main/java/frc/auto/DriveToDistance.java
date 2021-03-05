package frc.auto;

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
    // reset the encoders in initializing
    Robot.driveTrain.reset(); 
  }

  // gets the distance the robot has traveled
  private double getDistance() {
    return Math.abs((Robot.driveTrain.getLeftDistanceInFeet()) + (Robot.driveTrain.getRightDistanceInFeet()) / 2);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // If we've driven 5 feet set the drivetrain's speed and rotation to 0, if not set speed to 50%
    System.out.println("executed"); // printing for troubleshooting - executed for execute loop
    if (Math.abs(getDistance()) >= 5){
      Robot.driveTrain.joystickDrive(0, 0);
   } else{
     Robot.driveTrain.joystickDrive(0.5, 0);
   }


   // these were previous attempts that may have some merit but didn't work when tested, but may be something that we look back into later so I don't want to delete these
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

    if(Robot.driveTrain.getLeftDistanceInFeet() < 5){
      end = false;
    } else if(Robot.driveTrain.getLeftDistanceInFeet() >= 5){
      end = true;
    }
    // print statement for troubleshooting - end is true or end is false
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
