// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Full Bot Drive", group = "MCA EAGLES PROGRAMS")
public class fullBotDrive extends LinearOpMode {

    double speedFactor = 1.0;

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor viperSlider = null;
    DcMotor sliderArm = null;
    DcMotor suspensionArm = null;
    Servo clawWrist = null;
    Servo clawLeft = null;
    Servo clawRight = null;
    Servo planeLauncher = null;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        viperSlider = hardwareMap.dcMotor.get("Viper_Slider");
        sliderArm = hardwareMap.dcMotor.get("Slider_Rotation");
        suspensionArm = hardwareMap.dcMotor.get("Suspension_Arm");

        // Hardware Map All Servos
        planeLauncher = hardwareMap.servo.get("Plane_Launcher");
        clawLeft = hardwareMap.servo.get("Claw_Left");
        clawRight = hardwareMap.servo.get("Claw_Right");
        clawWrist = hardwareMap.servo.get("Claw_Wrist");

        // Set The Motors To Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reverse Direction Of One Claw
        clawRight.setDirection(Servo.Direction.REVERSE);

        // Set The Slider Arm To Reset The Motor To 0 At Start And Set The Initial Target To Start Position
        sliderArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArm.setTargetPosition(0);

        // Set the motor to run to the position mode. (NOTE: YOU NEED A TARGET SET BEFORE THIS MODE IS ENGAGED TO PREVENT ERROR)
        sliderArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set the arm to the speedFactor power.
        sliderArm.setPower(speedFactor);

        // Make locks variable.
        boolean locksActive = true;

        // Make auto placer variable.
        boolean autoPlace = true;

        // Make an int to store the current position of the claw setup.
        int posClaw = 0;

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {

            // Set drivetrain to move based off of the inputs of the left and right sticks of gamepad 1.
            frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * 0.8);
            backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * 0.8);
            frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * 0.8);
            backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * 0.8);

            // If the left trigger of gamepad 2 is activated, move the arm down. Set the target position up as the current position plus the value of our left trigger times a constant and our speedFactor.
            if (gamepad2.left_trigger != 0) {
                sliderArm.setTargetPosition((int) (sliderArm.getCurrentPosition() + speedFactor * 175 * gamepad2.left_trigger));
            }

            // Also do the same thing for the right trigger but subtract instead.
            else if (gamepad2.right_trigger != 0) {
                sliderArm.setTargetPosition((int) (sliderArm.getCurrentPosition() - speedFactor * 175 * gamepad2.right_trigger));
            }

            // If the arm tries to go below the initial, set to to 0, the initial.
            if (sliderArm.getTargetPosition() < 0) {
                sliderArm.setTargetPosition(0);
            }

            // If the a button is pressed on gamepad 2, lower the suspension arm.
            if (gamepad2.a) {
                suspensionArm.setPower(1);
            }

            // If the b button is pressed on gamepad 2, raise the suspension arm.
            else if (gamepad2.b) {
                suspensionArm.setPower(-1);
            }

            // Otherwise, stop the arm.
            else {
                suspensionArm.setPower(0);
            }

            // If the dpad down button is pressed, set the plane launcher to down position and lock in.
            if (gamepad2.dpad_down) {
                planeLauncher.setPosition(0);
            }

            // If the dpad up button is pressed, set the plane launcher to up position and release plane.
            else if (gamepad2.dpad_up) {
                planeLauncher.setPosition(0.5);
            }

            // TODO: Add time since last press implementation.

            // If gamepad 2 dpad left is pressed, toggle between true and false for autoPlace.
            if (gamepad2.dpad_left && gamepad2.x) {
                autoPlace = !autoPlace;
            }

            // If gamepad 2 dpad right is pressed, toggle between true and false for locksActive.
            if (gamepad2.dpad_right && gamepad2.y) {
                locksActive = !locksActive;
            }

            // Reset posClaw if right toggle is clicked down.
            if (gamepad2.right_stick_button) {
                posClaw = 0;
            }

            // If autoplace is active, pick up pixel automatically
            if (autoPlace) {
                if (gamepad2.left_stick_button) {

                    // Take into account the current position and choose a move after adding one to the position we want it to be.
                    switch (posClaw++ % 3) {

                        // Choice 0 is wrist on ground, open claw.
                        case 0: {

                            // Put arm down.
                            sliderArm.setTargetPosition(0);

                            // Wait a bit.
                            sleep(250);

                            // Put wrist down.
                            clawWrist.setPosition(0.66);

                            // Wait a bit.
                            sleep(500);

                            // Open claw.
                            clawLeft.setPosition(0.50);
                            clawRight.setPosition(0.35);

                            break;
                        }

                        // Choice 1 is pick up pixel, wrist back, raise arm.
                        case 1: {
                            // Close claw.
                            clawLeft.setPosition(0.25);
                            clawRight.setPosition(0.15);

                            // Wait a bit.
                            sleep(500);

                            // Lift arm and wrist back.
                            clawWrist.setPosition(0.30);
                            sliderArm.setTargetPosition(200);
                            break;
                        }

                        // Choice 2 extends slider, places pixel, retracts slider, moves bot back.
                        case 2: {
                            // Extend viper slider forward.
                            viperSlider.setPower(-0.50);

                            // Wait for a bit.
                            sleep(1000);

                            // Stop slider.
                            viperSlider.setPower(0);

                            // Open claw.
                            clawLeft.setPosition(0.50);
                            clawRight.setPosition(0.35);

                            // Wait for a bit.
                            sleep(500);

                            // Move robot back.
                            frontLeft.setPower(-0.25);
                            frontRight.setPower(-0.25);
                            backLeft.setPower(-0.25);
                            backRight.setPower(-0.25);

                            // Retract slider.
                            viperSlider.setPower(0.50);

                            // Close claw.
                            clawLeft.setPosition(0.25);
                            clawRight.setPosition(0.15);

                            // Wait for a bit.
                            sleep(1000);

                            // Stop viper slider.
                            viperSlider.setPower(0);

                            // Lower arm but keep off ground.
                            sliderArm.setTargetPosition(200);
                        }
                    }
                }
            } else {
                // If the left bumper on gamepad 2 is active, set the claw to open up.
                if (gamepad2.left_bumper) {
                    clawLeft.setPosition(0.50);
                    clawRight.setPosition(0.35);
                }

                // If the right bumper on gamepad 2 is active, set the claw to close.
                else if (gamepad2.right_bumper) {
                    clawLeft.setPosition(0.25);
                    clawRight.setPosition(0.15);
                }

                // Set the viper slider to the current power of gamepad 2's right stick's y.
                viperSlider.setPower(gamepad2.right_stick_y);

                // Engage wrist with locks if the locks are active.
                if (locksActive) {

                    if (gamepad2.left_stick_y != 0) {

                        // Check if the position to be set is less than 0 and set it to 0.
                        if (clawWrist.getPosition() - gamepad2.left_stick_y / 250 < 0.30) {
                            clawWrist.setPosition(0.30);
                        } // 0.30 is the position just before the claw will touch the viper.

                        // Check if the position is set to more than 1 and set it to 1.
                        else if (clawWrist.getPosition() - gamepad2.left_stick_y / 250 > 0.66) {
                            clawWrist.setPosition(0.66);
                        } // 0.66 is the position where the claw is parallel to the ground.

                        // Otherwise, set the position to the current added to the value of the stick.
                        else {
                            clawWrist.setPosition(clawWrist.getPosition() - gamepad2.left_stick_y / 250);
                        }
                    }
                } else {
                    if (gamepad2.left_stick_y != 0) {

                        // Check if the position to be set is less than 0 and set it to 0.
                        if (clawWrist.getPosition() - gamepad2.left_stick_y / 250 < 0.29) {
                            clawWrist.setPosition(0.30);
                        } // 0.30 is the position just before the claw will touch the viper.

                        // Otherwise, set the position to the current added to the value of the stick.
                        else {
                            clawWrist.setPosition(clawWrist.getPosition() - gamepad2.left_stick_y / 250);
                        }
                    }
                }
            }

            // Have claw change direction as viper moves.
            // Have slider change length of travel as arm moves.
        }
    }
}
