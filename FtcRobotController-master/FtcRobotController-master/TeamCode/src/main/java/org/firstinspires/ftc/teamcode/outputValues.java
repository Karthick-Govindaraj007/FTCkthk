// Import Required Files
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// Send Code And Operating Mode To Game Board
@TeleOp(name = "Output Values", group = "MCA EAGLES PROGRAMS")
public class outputValues extends LinearOpMode {

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

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reverse Direction Of One Claw
        clawRight.setDirection(Servo.Direction.REVERSE);

        // Set The Slider Arm To Reset The Motor To 0 At Start And Run Using Encoder
        sliderArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sliderArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set The Viper Slider To Reset The Motor To 0 At Start And Run Using Encoder
        viperSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Only Start Code And Movement When Start Button Is Pressed
        waitForStart();

        while (opModeIsActive()) {
            // Add the information needed.
            telemetry.addData("Arm Position: ", sliderArm.getCurrentPosition());
            telemetry.addData("Wrist Position: ", clawWrist.getPosition());
            telemetry.addData("Slider Position: ", viperSlider.getCurrentPosition());

            // Update the telemetry.
            telemetry.update();
        }
    }
}
