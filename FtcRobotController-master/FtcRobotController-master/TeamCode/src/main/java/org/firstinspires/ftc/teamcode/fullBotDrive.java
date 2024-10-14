// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
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
    DcMotor viperV = null;
    DcMotor viperH = null;
    DcMotor susL = null;
    DcMotor susR = null;
    Servo sClawL = null;
    Servo sClawR = null;
    CRServo intake = null;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");
        viperV = hardwareMap.dcMotor.get("Viper_Vertical");
        viperH = hardwareMap.dcMotor.get("Viper_Horizontal");
        susL = hardwareMap.dcMotor.get("Suspension_Left");
        susR = hardwareMap.dcMotor.get("Suspension_Right");

        // Hardware Map Servos
        sClawL = hardwareMap.servo.get("Claw_Left");
        sClawR = hardwareMap.servo.get("Claw_Right");
        intake = hardwareMap.crservo.get("Intake");

        // Set The Motors To Brake
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {
            // Set drivetrain to move based off of the inputs of the left and right sticks of gamepad 1.
            frontLeft.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * speedFactor);
            backLeft.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * speedFactor);
            frontRight.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * speedFactor);
            backRight.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * speedFactor);

            // TODO: Set viper motors to run using encoders.

            if (-gamepad2.left_stick_y > 0) {
                viperV.setPower(-gamepad2.left_stick_y * speedFactor);
            } else if (-gamepad2.left_stick_y < 0) {
                viperV.setPower(-gamepad2.left_stick_y * speedFactor);
            } else {
                viperV.setPower(0);
            }

            if (-gamepad2.right_stick_y > 0) {
                viperH.setPower(-gamepad2.right_stick_y * speedFactor);
            } else if (-gamepad2.right_stick_y < 0) {
                viperH.setPower(-gamepad2.right_stick_y * speedFactor);
            } else {
                viperH.setPower(0);
            }

            if (gamepad2.b) {
                susL.setPower(1);
                susR.setPower(1);
            } else if (gamepad2.a) {
                susL.setPower(-1);
                susR.setPower(-1);
            } else {
                susL.setPower(0);
                susR.setPower(0);
            }

            if (gamepad2.right_trigger > 0) {
                intake.setPower(1);
            } else if (gamepad2.left_trigger > 0) {
                intake.setPower(-1);
            } else {
                intake.setPower(0);
            }

            // TODO: Fix positions of claw servos.

            if (gamepad2.x) {
                sClawL.setPosition(0.5);
                sClawR.setPosition(0.5);
            } else if (gamepad2.y) {
                sClawL.setPosition(0);
                sClawR.setPosition(0);
            }

            if (gamepad1.a) {
                speedFactor = 1.0;
            } else if (gamepad2.b) {
                speedFactor = 0.5;
            }

            telemetry.addData("Front Left Motor Power", frontLeft.getPower());
            telemetry.addData("Front Left Motor Position", frontLeft.getCurrentPosition());
            telemetry.addData("Front Right Motor Power", frontRight.getPower());
            telemetry.addData("Front Right Motor Position", frontRight.getCurrentPosition());
            telemetry.addData("Back Left Motor Power", backLeft.getPower());
            telemetry.addData("Back Left Motor Position", backLeft.getCurrentPosition());
            telemetry.addData("Back Right Motor Power", backRight.getPower());
            telemetry.addData("Back Right Motor Position", backRight.getCurrentPosition());
            telemetry.addData("Viper Vertical Motor Power", viperV.getPower());
            telemetry.addData("Viper Vertical Motor Position", viperV.getCurrentPosition());
            telemetry.addData("Viper Horizontal Motor Power", viperH.getPower());
            telemetry.addData("Viper Horizontal Motor Position", viperH.getCurrentPosition());
            telemetry.addData("Left Suspension Motor Power", susL.getPower());
            telemetry.addData("Left Suspension Motor Position", susL.getCurrentPosition());
            telemetry.addData("Right Suspension Motor Power", susR.getPower());
            telemetry.addData("Right Suspension Motor Position", susR.getCurrentPosition());
            telemetry.addData("Left Claw Servo Position", sClawL.getPosition());
            telemetry.addData("Right Claw Servo Position", sClawR.getPosition());
            telemetry.addData("Intake Motor Power", intake.getPower());
            telemetry.addData("Speed Factor", speedFactor);

            telemetry.update();
        }
    }
}