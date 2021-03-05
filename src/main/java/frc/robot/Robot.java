/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.auto.Auton;
import frc.subsystems.Climber;
import frc.subsystems.DriveTrain;
//import frc.subsystems.Climber;
//import frc.subsystems.DriveTrain;
import frc.subsystems.IntestineTransport;
import frc.subsystems.NoPIDShooter;
//import frc.subsystems.ShooterSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

// initialize subsystems
public static DriveTrain driveTrain = new DriveTrain();
public static IntestineTransport intestineTransport = new IntestineTransport();
//public static ShooterSubsystem shooter = new ShooterSubsystem();
public static NoPIDShooter noPIDShooter = new NoPIDShooter();
public static Climber climber = new Climber();

// initialize auton
 public static Command autonomousCommand = new Auton();

// initialize network table
NetworkTable table = NetworkTableInstance.getDefault().getTable("dashboard");

// Initialize OI
public static OI oi; 

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    oi = new OI();
    // instantiate the command for the auton period
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    outputData();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */

  @Override
  public void autonomousInit() {
    driveTrain.resetLeftEncoder();
//    Scheduler.getInstance().add(autonomousCommand);
    }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
//    Scheduler.getInstance().run();
    System.out.println("AUTO.RUN");
/*
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
*/
      }

  @Override
  public void teleopInit() {
    driveTrain.reset();
    }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }
  
  @Override
  public void testInit() {
}

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {
  }

  private void outputData(){
    table.getEntry("time").setString(DriverStation.getInstance().getMatchTime() < 0 ? "0:00" : Math.floor(DriverStation.getInstance().getMatchTime() / 60) + ":" + (DriverStation.getInstance().getMatchTime() % 60 < 10 ? "0" : "") + Math.floor(DriverStation.getInstance().getMatchTime() % 60));
    table.getEntry("Number-Onboard").setNumber(intestineTransport.CellsOnboard());
  //  table.getEntry("Slow-Mode-State").setBoolean(driveTrain.slowMode());
  //  table.getEntry("Reversed-Mode-State").setBoolean(driveTrain.reversed());
  }
}