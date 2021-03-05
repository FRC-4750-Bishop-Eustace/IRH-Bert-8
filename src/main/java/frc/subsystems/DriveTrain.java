package frc.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.commands.TankDrive;
import frc.robot.RobotMap;

public class DriveTrain extends Subsystem {

    // Create motors
    public WPI_TalonSRX leftMaster, leftFollowerOne, leftFollowerTwo, rightMaster, rightFollowerOne, rightFollowerTwo;
    // Create drive
    DifferentialDrive robotDrive;
    boolean slowMode = true;
    boolean reverseDrive = false;

    // encoder & unit conversion
    public Encoder leftDriveEncoder, rightDriveEncoder;

    public double distancePerPulse = 0.00447; // ((7 * Math.PI) / 4096) / 12;

    public DriveTrain() {
        // Initialize motors
        leftMaster = new WPI_TalonSRX(RobotMap.LEFT_MASTER_ID);
        leftFollowerOne = new WPI_TalonSRX(RobotMap.LEFT_FOLLOWER_ONE_ID);
        leftFollowerTwo = new WPI_TalonSRX(RobotMap.LEFT_FOLLOWER_TWO_ID);
        rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_MASTER_ID);
        rightFollowerOne = new WPI_TalonSRX(RobotMap.RIGHT_FOLLOWER_ONE_ID);
        rightFollowerTwo = new WPI_TalonSRX(RobotMap.RIGHT_FOLLOWER_TWO_ID);

        // encoders
        leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B, false, EncodingType.k4X);
        rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B, true, EncodingType.k4X);

        leftDriveEncoder.setDistancePerPulse(distancePerPulse);
        rightDriveEncoder.setDistancePerPulse(distancePerPulse);

        // initialize encoders
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        // Set motor and encoder phases
        leftMaster.setInverted(false);
        leftMaster.setSensorPhase(true);
        rightMaster.setInverted(false);
        rightMaster.setSensorPhase(false);

        // Set motors to follow masters
        leftFollowerOne.follow(leftMaster);
        leftFollowerTwo.follow(leftMaster);
        rightFollowerOne.follow(rightMaster);
        rightFollowerTwo.follow(rightMaster);
        leftFollowerOne.setInverted(InvertType.FollowMaster);
        leftFollowerTwo.setInverted(InvertType.FollowMaster);
        rightFollowerOne.setInverted(InvertType.FollowMaster);
        rightFollowerTwo.setInverted(InvertType.FollowMaster);
        
        robotDrive = new DifferentialDrive(leftMaster, rightMaster);
        // Stop "output not updated often enough" error from printing
        robotDrive.setSafetyEnabled(false); 
    }

    // Get the counts

    public double getLeftCount(){
       return leftDriveEncoder.getRaw();
    }

    public double getRightCount(){
        return rightDriveEncoder.getRaw();
     }

    //Get the distance in feet

    public double getLeftDistanceInFeet(){
        // raw distance * distancePerPulse
       return leftDriveEncoder.getDistance();
    }

    public double getRightDistanceInFeet(){
        // raw distance * distancePerPulse
       return rightDriveEncoder.getDistance();
    }

    // Reset Options

    public void resetLeftEncoder(){
        leftDriveEncoder.reset();
    }

    public void resetRightEncoder(){
//        rightDriveEncoder.reset();
    }

    public void reset(){
        resetLeftEncoder();;
//        resetRightEncoder();
    }

    /**
     * Passes speed and rotate values to the drive train
     * 
     * @param speed  + is forward, - is backward
     * @param rotate + is right, - is left
     */

     
    public void joystickDrive(double speed, double rotate) {
        robotDrive.arcadeDrive(cube(speed), cube(rotate));
        // .28, .15
    }

    /**
     * Reverses the drive train
     * 
     * @param _reversed true for reversed, false for regular
     * @return
     */

     
    public void reverseDriveTrain(boolean _reversed) {
      reverseDrive = _reversed;
    }

    /**
     * Returns whether the drive train is reversed
     */

    
    public boolean reversed() {
        return reverseDrive;
    }

     /**
     * Makes the drive train go to slow mode
     * 
     * @param _slowMode true for slow, false for regular
     */

    
     public void slowModeDrive(boolean _slowMode){
        slowMode = _slowMode;
     }
    
    public boolean slowMode(){
        return slowMode;
    }
    /**
     * Turns the drive train
     * 
     * @param speed + is right, - is left
     */

    
    public void turn(double speed) {
        robotDrive.arcadeDrive(0, -speed);
    }

    /**
     * Brakes all motors on the drive train
     */

    
    public void brake() {
        leftMaster.stopMotor();
        rightMaster.stopMotor();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

    /**
     * Desensitizes the joystick values at low speeds
     */

    
    protected double cube(double value) {
        return 0.2 * Math.pow(value, 3) + (1 - 0.2) * value;
 
    }
}
