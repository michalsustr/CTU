/*
 * File: nxtway_app.c
 *
 * Real-Time Workshop code generated for Simulink model nxtway_app.
 *
 * Model version                        : 1.729
 * Real-Time Workshop file version      : 7.6  (R2010b)  03-Aug-2010
 * Real-Time Workshop file generated on : Tue Mar  1 17:04:33 2011
 * TLC version                          : 7.6 (Jul 13 2010)
 * C/C++ source code generated on       : Tue Mar  1 17:04:33 2011
 *
 * Target selection: ert.tlc
 * Embedded hardware selection: ARM7
 * Code generation objectives: Unspecified
 * Validation result: Not run
 */

#include "nxtway_app.h"
#include "nxtway_app_private.h"

/* Block signals (auto storage) */
BlockIO rtB;

/* Block states (auto storage) */
D_Work rtDWork;

/* Output and update for exported function: task_init */
void task_init(void)
{
  /* S-Function (fcncallgen): '<S1>/expFcn' incorporates:
   *  SubSystem: '<S5>/task_init'
   */

  /* DataStoreWrite: '<S6>/Data Store Write7' incorporates:
   *  DataStoreRead: '<S10>/Data Store Read'
   *  DataTypeConversion: '<S6>/Data Type Conversion1'
   */
  rtDWork.battery = (real32_T)ecrobot_get_battery_voltage();

  /* DataStoreWrite: '<S6>/Data Store Write5' incorporates:
   *  Constant: '<S6>/Constant1'
   */
  rtDWork.flag_mode = FALSE;

  /* DataStoreWrite: '<S6>/Data Store Write1' incorporates:
   *  DataStoreRead: '<S11>/Data Store Read'
   *  DataTypeConversion: '<S6>/Data Type Conversion2'
   */
  rtDWork.gyro_offset = (real32_T)ecrobot_get_gyro_sensor(NXT_PORT_S4);

  /* DataStoreWrite: '<S6>/Data Store Write8' incorporates:
   *  DataStoreRead: '<S13>/Data Store Read'
   */
  rtDWork.start_time = ecrobot_get_systick_ms();
}

/* Start for exported function: task_ts1 */
void task_ts1_Start(void)
{
  /* Start for S-Function (fcncallgen): '<S2>/expFcn' incorporates:
   *  Start for SubSystem: '<S5>/task_ts1'
   */
}

/* Output and update for exported function: task_ts1 */
void task_ts1(void)
{
  /* local block i/o variables */
  int16_T rtb_DataTypeConversion2_gl;
  int8_T rtb_DataTypeConversion;
  int8_T rtb_DataTypeConversion6;
  boolean_T rtb_DataStoreRead;
  real32_T rtb_UnitDelay;
  real32_T rtb_IntegralGain;
  real32_T rtb_UnitDelay_l;
  boolean_T rtb_DataStoreRead1_j;
  boolean_T rtb_DataStoreRead2_i;
  int8_T rtb_DataTypeConversion_m[2];
  int8_T rtb_DataTypeConversion2_c;
  real32_T rtb_DataTypeConversion4;
  real32_T rtb_DataTypeConversion3;
  real32_T rtb_theta;
  real32_T rtb_Sum_a;
  real32_T rtb_Sum_b;
  real32_T rtb_psidot;
  real32_T rtb_Gain2_d;
  real32_T rtb_Sum_g;
  uint8_T rtb_UnitDelay_lv;
  int8_T rtb_Switch;
  int32_T i;
  real32_T rtb_UnitDelay_l_0[4];
  real32_T rtb_theta_0[4];
  int8_T rtb_Switch1_idx;
  real32_T u;

  /* S-Function (fcncallgen): '<S2>/expFcn' incorporates:
   *  SubSystem: '<S5>/task_ts1'
   */

  /* DataStoreRead: '<S7>/Data Store Read' */
  rtb_DataStoreRead = rtDWork.flag_start;

  /* Outputs for enable SubSystem: '<S7>/Balance & Drive Control' incorporates:
   *  EnablePort: '<S14>/Enable'
   */
  if (rtDWork.flag_start) {
    /* UnitDelay: '<S28>/Unit Delay' */
    rtb_UnitDelay = rtDWork.UnitDelay_DSTATE;

    /* Gain: '<S17>/Integral Gain' */
    rtb_IntegralGain = -0.44721359F * rtDWork.UnitDelay_DSTATE;

    /* UnitDelay: '<S34>/Unit Delay' */
    rtb_UnitDelay_l = rtDWork.UnitDelay_DSTATE_j;

    /* DataStoreRead: '<S14>/Data Store Read1' */
    rtb_DataStoreRead1_j = rtDWork.flag_avoid;

    /* Switch: '<S41>/Switch' incorporates:
     *  Constant: '<S41>/Constant'
     *  Constant: '<S41>/Constant1'
     */
    if (rtDWork.flag_avoid) {
      rtb_Switch = 0;
    } else {
      rtb_Switch = 100;
    }

    /* Switch: '<S41>/Switch2' incorporates:
     *  Constant: '<S41>/Constant2'
     *  Constant: '<S41>/Constant3'
     */
    if (rtDWork.flag_avoid) {
      rtb_DataTypeConversion2_c = 100;
    } else {
      rtb_DataTypeConversion2_c = 0;
    }

    /* Switch: '<S20>/Switch2' incorporates:
     *  Constant: '<S20>/Constant1'
     *  DataStoreRead: '<S20>/Data Store Read1'
     */
    if (rtDWork.flag_auto) {
      rtb_Switch1_idx = rtb_DataTypeConversion2_c;
    } else {
      rtb_Switch = 0;
      rtb_Switch1_idx = 0;
    }

    /* DataStoreRead: '<S20>/Data Store Read2' */
    rtb_DataStoreRead2_i = rtDWork.flag_mode;

    /* S-Function Block: <S14>/Bluetooth Rx Read */
    ecrobot_read_bt_packet(rtB.BluetoothRxRead, 32);

    /* DataTypeConversion: '<S42>/Data Type Conversion' */
    for (i = 0; i < 2; i++) {
      rtb_DataTypeConversion_m[i] = (int8_T)rtB.BluetoothRxRead[i];
    }

    /* Switch: '<S42>/Switch' incorporates:
     *  Constant: '<S42>/Constant'
     */
    if (rtb_DataStoreRead1_j) {
      rtb_DataTypeConversion2_c = 100;
    } else {
      rtb_DataTypeConversion2_c = rtb_DataTypeConversion_m[0];
    }

    /* Gain: '<S42>/Gain1' */
    rtb_DataTypeConversion2_c = (int8_T)(-rtb_DataTypeConversion2_c);

    /* Switch: '<S20>/Switch1' */
    if (!rtb_DataStoreRead2_i) {
      rtb_Switch = rtb_DataTypeConversion2_c;
      rtb_Switch1_idx = rtb_DataTypeConversion_m[1];
    }

    /* Sum: '<S35>/Sum' incorporates:
     *  Constant: '<S26>/Constant6'
     *  DataTypeConversion: '<S14>/Data Type Conversion2'
     *  Gain: '<S26>/Gain1'
     *  Gain: '<S35>/Gain2'
     *  Gain: '<S35>/Gain3'
     *  Product: '<S26>/Divide'
     *  UnitDelay: '<S35>/Unit Delay'
     */
    rtb_Sum_g = (real32_T)rtb_Switch / 100.0F * 7.5F * 0.004F + 0.996F *
      rtDWork.UnitDelay_DSTATE_e;

    /* DataTypeConversion: '<S14>/Data Type Conversion3' incorporates:
     *  DataStoreRead: '<S21>/Data Store Read'
     */
    rtb_DataTypeConversion3 = (real32_T)ecrobot_get_motor_rev(NXT_PORT_C);

    /* DataTypeConversion: '<S14>/Data Type Conversion4' incorporates:
     *  DataStoreRead: '<S22>/Data Store Read'
     */
    rtb_DataTypeConversion4 = (real32_T)ecrobot_get_motor_rev(NXT_PORT_B);

    /* Gain: '<S27>/Gain' incorporates:
     *  Gain: '<S27>/deg2rad'
     *  Gain: '<S27>/deg2rad1'
     *  Sum: '<S27>/Sum1'
     *  Sum: '<S27>/Sum4'
     *  Sum: '<S27>/Sum6'
     *  UnitDelay: '<S38>/Unit Delay'
     */
    rtb_theta = ((0.0174532924F * rtb_DataTypeConversion3 +
                  rtDWork.UnitDelay_DSTATE_h) + (0.0174532924F *
      rtb_DataTypeConversion4 + rtDWork.UnitDelay_DSTATE_h)) * 0.5F;

    /* Sum: '<S39>/Sum' incorporates:
     *  Gain: '<S39>/Gain2'
     *  Gain: '<S39>/Gain3'
     *  UnitDelay: '<S39>/Unit Delay'
     */
    rtb_Sum_a = 0.2F * rtb_theta + 0.8F * rtDWork.UnitDelay_DSTATE_hp;

    /* DataTypeConversion: '<S14>/Data Type Conversion5' incorporates:
     *  DataStoreRead: '<S19>/Data Store Read'
     */
    rtb_psidot = (real32_T)ecrobot_get_gyro_sensor(NXT_PORT_S4);

    /* DataStoreWrite: '<S36>/Data Store Write1' incorporates:
     *  DataStoreRead: '<S36>/Data Store Read2'
     *  Gain: '<S36>/Gain1'
     *  Gain: '<S36>/Gain4'
     *  Sum: '<S36>/Sum'
     */
    rtDWork.gyro_offset = 0.999F * rtDWork.gyro_offset + 0.001F * rtb_psidot;

    /* Gain: '<S27>/deg2rad2' incorporates:
     *  DataStoreRead: '<S36>/Data Store Read1'
     *  Sum: '<S27>/Sum2'
     */
    rtb_psidot = (rtb_psidot - rtDWork.gyro_offset) * 0.0174532924F;

    /* Gain: '<S25>/Gain' incorporates:
     *  Constant: '<S26>/Constant2'
     *  Constant: '<S26>/Constant3'
     *  Constant: '<S30>/Constant'
     *  DataStoreRead: '<S17>/Data Store Read1'
     *  Gain: '<S17>/Feedback Gain'
     *  Gain: '<S30>/Gain3'
     *  Gain: '<S37>/Gain'
     *  Product: '<S25>/Product'
     *  Sum: '<S17>/Sum'
     *  Sum: '<S17>/Sum2'
     *  Sum: '<S30>/Sum2'
     *  Sum: '<S37>/Sum'
     *  UnitDelay: '<S37>/Unit Delay'
     *  UnitDelay: '<S38>/Unit Delay'
     */
    rtb_UnitDelay_l_0[0] = rtb_UnitDelay_l;
    rtb_UnitDelay_l_0[1] = 0.0F;
    rtb_UnitDelay_l_0[2] = rtb_Sum_g;
    rtb_UnitDelay_l_0[3] = 0.0F;
    rtb_theta_0[0] = rtb_theta;
    rtb_theta_0[1] = rtDWork.UnitDelay_DSTATE_h;
    rtb_theta_0[2] = (rtb_Sum_a - rtDWork.UnitDelay_DSTATE_m) * 250.0F;
    rtb_theta_0[3] = rtb_psidot;
    rtb_Sum_b = 0.0F;
    for (i = 0; i < 4; i++) {
      rtb_Sum_b += (rtb_UnitDelay_l_0[i] - rtb_theta_0[i]) *
        rtConstP.FeedbackGain_Gain[i];
    }

    rtb_Sum_b = (rtb_IntegralGain + rtb_Sum_b) / (0.001089F * rtDWork.battery -
      0.625F) * 100.0F;

    /* DataTypeConversion: '<S14>/Data Type Conversion1' */
    rtb_IntegralGain = (real32_T)rtb_Switch1_idx;

    /* Gain: '<S26>/Gain2' incorporates:
     *  Constant: '<S26>/Constant1'
     *  Product: '<S26>/Divide1'
     */
    rtb_Gain2_d = rtb_IntegralGain / 100.0F * 25.0F;

    /* DataTypeConversion: '<S14>/Data Type Conversion' incorporates:
     *  Saturate: '<S25>/Saturation'
     *  Sum: '<S25>/Sum'
     */
    u = rtb_Sum_b + rtb_Gain2_d;
    rtb_DataTypeConversion = (int8_T)(u >= 100.0F ? 100.0F : u <= -100.0F ?
      -100.0F : u);

    /* DataStoreWrite: '<S23>/Data Store Write' */
    ecrobot_set_motor_mode_speed(NXT_PORT_C, 1, rtb_DataTypeConversion);

    /* Switch: '<S25>/Switch3' incorporates:
     *  Constant: '<S25>/Constant5'
     *  Constant: '<S25>/Constant6'
     *  DataTypeConversion: '<S25>/Data Type Conversion2'
     */
    if ((int32_T)rtb_IntegralGain != 0) {
      rtb_DataStoreRead1_j = TRUE;
    } else {
      rtb_DataStoreRead1_j = FALSE;
    }

    /* Sum: '<S29>/Sum1' */
    rtb_DataTypeConversion3 -= rtb_DataTypeConversion4;

    /* Outputs for enable SubSystem: '<S29>/Set theta_offset' incorporates:
     *  Constant: '<S32>/Constant'
     *  Constant: '<S32>/Constant1'
     *  EnablePort: '<S33>/Enable'
     *  Logic: '<S32>/Logical Operator'
     *  RelationalOperator: '<S32>/Relational Operator'
     *  RelationalOperator: '<S32>/Relational Operator1'
     *  UnitDelay: '<S32>/Unit Delay'
     */
    if ((!rtb_DataStoreRead1_j) && rtDWork.UnitDelay_DSTATE_c) {
      /* Inport: '<S33>/theta_diff' */
      rtB.theta_diff = rtb_DataTypeConversion3;
    }

    /* end of Outputs for SubSystem: '<S29>/Set theta_offset' */

    /* Switch: '<S25>/Switch2' incorporates:
     *  Constant: '<S25>/Constant1'
     *  Gain: '<S25>/Gain2'
     *  Sum: '<S29>/Sum5'
     */
    if (rtb_DataStoreRead1_j) {
      rtb_DataTypeConversion4 = 0.0F;
    } else {
      rtb_DataTypeConversion4 = (rtb_DataTypeConversion3 - rtB.theta_diff) *
        0.35F;
    }

    /* Sum: '<S25>/Sum2' incorporates:
     *  Sum: '<S25>/Sum1'
     */
    rtb_DataTypeConversion4 += rtb_Sum_b - rtb_Gain2_d;

    /* Saturate: '<S25>/Saturation1' */
    rtb_DataTypeConversion4 = rtb_DataTypeConversion4 >= 100.0F ? 100.0F :
      rtb_DataTypeConversion4 <= -100.0F ? -100.0F : rtb_DataTypeConversion4;

    /* DataTypeConversion: '<S14>/Data Type Conversion6' */
    rtb_DataTypeConversion6 = (int8_T)rtb_DataTypeConversion4;

    /* DataStoreWrite: '<S24>/Data Store Write' */
    ecrobot_set_motor_mode_speed(NXT_PORT_B, 1, rtb_DataTypeConversion6);

    /* Sum: '<S38>/Sum' incorporates:
     *  Gain: '<S38>/Gain'
     *  UnitDelay: '<S38>/Unit Delay'
     */
    rtb_DataTypeConversion3 = 0.004F * rtb_psidot + rtDWork.UnitDelay_DSTATE_h;

    /* UnitDelay: '<S16>/Unit Delay' */
    rtb_UnitDelay_lv = rtDWork.UnitDelay_DSTATE_k;

    /* Switch: '<S16>/Switch3' incorporates:
     *  Constant: '<S16>/Constant5'
     *  Constant: '<S16>/Constant6'
     */
    if (rtDWork.UnitDelay_DSTATE_k != 0) {
      rtb_DataStoreRead2_i = FALSE;
    } else {
      rtb_DataStoreRead2_i = TRUE;
    }

    /* Outputs for enable SubSystem: '<S14>/Data Logging' incorporates:
     *  EnablePort: '<S18>/Enable'
     */
    if (rtb_DataStoreRead2_i) {
      /* DataTypeConversion: '<S18>/Data Type Conversion2' incorporates:
       *  Constant: '<S40>/LSB'
       *  Gain: '<S40>/rad2deg'
       *  Product: '<S40>/Divide'
       *  UnitDelay: '<S38>/Unit Delay'
       */
      rtb_Sum_b = 57.2957802F * rtDWork.UnitDelay_DSTATE_h / 0.0009765625F;
      if ((rtb_Sum_b < 8.388608E+6F) && (rtb_Sum_b > -8.388608E+6F)) {
        rtb_Sum_b = floorf(rtb_Sum_b + 0.5F);
      }

      rtb_DataTypeConversion2_gl = (int16_T)rtb_Sum_b;

      /* S-Function Block: <S18>/NXT GamePad  ADC Data Logger */
      ecrobot_bt_adc_data_logger(rtb_DataTypeConversion, rtb_DataTypeConversion6,
        rtb_DataTypeConversion2_gl, 0, 0, 0);
    }

    /* end of Outputs for SubSystem: '<S14>/Data Logging' */

    /* Sum: '<S16>/Sum' incorporates:
     *  Constant: '<S16>/Constant1'
     */
    rtb_UnitDelay_lv = (uint8_T)(1U + (uint32_T)rtb_UnitDelay_lv);

    /* Switch: '<S16>/Switch1' incorporates:
     *  Constant: '<S16>/Constant2'
     *  Constant: '<S16>/Constant3'
     *  RelationalOperator: '<S16>/Relational Operator'
     *  Update for UnitDelay: '<S16>/Unit Delay'
     */
    if (25 == rtb_UnitDelay_lv) {
      rtDWork.UnitDelay_DSTATE_k = 0U;
    } else {
      rtDWork.UnitDelay_DSTATE_k = rtb_UnitDelay_lv;
    }

    /* Update for UnitDelay: '<S28>/Unit Delay' incorporates:
     *  Gain: '<S28>/Gain'
     *  Sum: '<S17>/Sum1'
     *  Sum: '<S28>/Sum'
     */
    rtDWork.UnitDelay_DSTATE = (rtb_UnitDelay_l - rtb_theta) * 0.004F +
      rtb_UnitDelay;

    /* Update for UnitDelay: '<S34>/Unit Delay' incorporates:
     *  Gain: '<S34>/Gain'
     *  Sum: '<S34>/Sum'
     */
    rtDWork.UnitDelay_DSTATE_j = 0.004F * rtb_Sum_g + rtb_UnitDelay_l;

    /* Update for UnitDelay: '<S35>/Unit Delay' */
    rtDWork.UnitDelay_DSTATE_e = rtb_Sum_g;

    /* Update for UnitDelay: '<S38>/Unit Delay' */
    rtDWork.UnitDelay_DSTATE_h = rtb_DataTypeConversion3;

    /* Update for UnitDelay: '<S39>/Unit Delay' */
    rtDWork.UnitDelay_DSTATE_hp = rtb_Sum_a;

    /* Update for UnitDelay: '<S37>/Unit Delay' */
    rtDWork.UnitDelay_DSTATE_m = rtb_Sum_a;

    /* Update for UnitDelay: '<S32>/Unit Delay' */
    rtDWork.UnitDelay_DSTATE_c = rtb_DataStoreRead1_j;
  }

  /* end of Outputs for SubSystem: '<S7>/Balance & Drive Control' */

  /* Outputs for enable SubSystem: '<S7>/Gyro Calibration' incorporates:
   *  EnablePort: '<S15>/Enable'
   *  Logic: '<S7>/Logical Operator'
   */
  if (!rtb_DataStoreRead) {
    /* DataStoreWrite: '<S15>/Data Store Write1' incorporates:
     *  DataStoreRead: '<S15>/Data Store Read2'
     *  DataStoreRead: '<S43>/Data Store Read'
     *  DataTypeConversion: '<S15>/Data Type Conversion2'
     *  Gain: '<S15>/Gain1'
     *  Gain: '<S15>/Gain4'
     *  Sum: '<S15>/Sum'
     */
    rtDWork.gyro_offset = 0.8F * rtDWork.gyro_offset + 0.2F * (real32_T)
      ecrobot_get_gyro_sensor(NXT_PORT_S4);
  }

  /* end of Outputs for SubSystem: '<S7>/Gyro Calibration' */
}

/* Output and update for exported function: task_ts2 */
void task_ts2(void)
{
  boolean_T rtb_DataStoreRead2_j;

  /* S-Function (fcncallgen): '<S3>/expFcn' incorporates:
   *  SubSystem: '<S5>/task_ts2'
   */

  /* Outputs for enable SubSystem: '<S8>/Autonomous Drive' incorporates:
   *  DataStoreRead: '<S8>/Data Store Read1'
   *  EnablePort: '<S44>/Enable'
   */
  if (rtDWork.flag_mode) {
    /* DataStoreRead: '<S44>/Data Store Read2' */
    rtb_DataStoreRead2_j = rtDWork.flag_avoid;

    /* Outputs for enable SubSystem: '<S44>/Forward Run' incorporates:
     *  EnablePort: '<S46>/Enable'
     *  Logic: '<S44>/Logical Operator1'
     */
    if (!rtDWork.flag_avoid) {
      /* DataStoreWrite: '<S46>/Data Store Write1' incorporates:
       *  Constant: '<S46>/Constant'
       *  DataStoreRead: '<S49>/Data Store Read'
       *  RelationalOperator: '<S46>/Relational Operator'
       */
      rtDWork.flag_avoid = (ecrobot_get_sonar_sensor(NXT_PORT_S2) <= 20);

      /* DataStoreRead: '<S48>/Data Store Read' */
      rtB.RevolutionSensor_C = ecrobot_get_motor_rev(NXT_PORT_C);
    }

    /* end of Outputs for SubSystem: '<S44>/Forward Run' */

    /* Outputs for enable SubSystem: '<S44>/Rotate Right' incorporates:
     *  EnablePort: '<S47>/Enable'
     */
    if (rtb_DataStoreRead2_j) {
      /* DataStoreWrite: '<S47>/Data Store Write1' incorporates:
       *  Constant: '<S47>/Constant1'
       *  DataStoreRead: '<S50>/Data Store Read'
       *  RelationalOperator: '<S47>/Relational Operator1'
       *  Sum: '<S47>/Sum1'
       */
      rtDWork.flag_avoid = (ecrobot_get_motor_rev(NXT_PORT_C) -
                            rtB.RevolutionSensor_C <= 210);
    }

    /* end of Outputs for SubSystem: '<S44>/Rotate Right' */
  }

  /* end of Outputs for SubSystem: '<S8>/Autonomous Drive' */

  /* Outputs for enable SubSystem: '<S8>/Remote Control Drive' incorporates:
   *  DataStoreRead: '<S8>/Data Store Read1'
   *  EnablePort: '<S45>/Enable'
   *  Logic: '<S8>/Logical Operator'
   */
  if (!rtDWork.flag_mode) {
    /* DataStoreWrite: '<S45>/Data Store Write1' incorporates:
     *  Constant: '<S45>/Constant'
     *  DataStoreRead: '<S51>/Data Store Read'
     *  RelationalOperator: '<S45>/Relational Operator'
     */
    rtDWork.flag_avoid = (ecrobot_get_sonar_sensor(NXT_PORT_S2) <= 20);
  }

  /* end of Outputs for SubSystem: '<S8>/Remote Control Drive' */
}

/* Start for exported function: task_ts3 */
void task_ts3_Start(void)
{
  /* Start for S-Function (fcncallgen): '<S4>/expFcn' incorporates:
   *  Start for SubSystem: '<S5>/task_ts3'
   */
}

/* Output and update for exported function: task_ts3 */
void task_ts3(void)
{
  uint32_T rtb_Sum1_i;
  boolean_T rtb_RelationalOperator_e0;

  /* S-Function (fcncallgen): '<S4>/expFcn' incorporates:
   *  SubSystem: '<S5>/task_ts3'
   */

  /* DataStoreWrite: '<S9>/Data Store Write1' incorporates:
   *  DataStoreRead: '<S52>/Data Store Read'
   *  DataStoreRead: '<S9>/Data Store Read2'
   *  DataTypeConversion: '<S9>/Data Type Conversion2'
   *  Gain: '<S9>/Gain1'
   *  Gain: '<S9>/Gain2'
   *  Sum: '<S9>/Sum'
   */
  rtDWork.battery = 0.8F * rtDWork.battery + 0.2F * (real32_T)
    ecrobot_get_battery_voltage();

  /* Sum: '<S9>/Sum1' incorporates:
   *  DataStoreRead: '<S54>/Data Store Read'
   *  DataStoreRead: '<S9>/Data Store Read1'
   */
  rtb_Sum1_i = ecrobot_get_systick_ms() - rtDWork.start_time;

  /* RelationalOperator: '<S9>/Relational Operator' incorporates:
   *  Constant: '<S9>/Constant'
   */
  rtb_RelationalOperator_e0 = (rtb_Sum1_i >= 1000U);

  /* DataStoreWrite: '<S9>/Data Store Write' */
  rtDWork.flag_start = rtb_RelationalOperator_e0;

  /* DataStoreWrite: '<S9>/Data Store Write2' incorporates:
   *  Constant: '<S9>/Constant3'
   *  RelationalOperator: '<S9>/Relational Operator1'
   */
  rtDWork.flag_auto = (rtb_Sum1_i >= 5000U);

  /* Outputs for enable SubSystem: '<S9>/Make Sound' incorporates:
   *  EnablePort: '<S53>/Enable'
   *  RelationalOperator: '<S9>/Relational Operator2'
   *  UnitDelay: '<S9>/Unit Delay'
   */
  if ((int32_T)rtb_RelationalOperator_e0 != (int32_T)rtDWork.UnitDelay_DSTATE_a)
  {
    /* S-Function Block: <S53>/Sound Tone Write */
    ecrobot_sound_tone(440U, 500U, 70);
  }

  /* end of Outputs for SubSystem: '<S9>/Make Sound' */

  /* Update for UnitDelay: '<S9>/Unit Delay' */
  rtDWork.UnitDelay_DSTATE_a = rtb_RelationalOperator_e0;
}

/* Model initialize function */
void nxtway_app_initialize(void)
{
  /* Start for S-Function (expFcnGen): '<Root>/__FcnCallGen__0' incorporates:
   *  Start for SubSystem: '<Root>/__ExpFcn__1'
   *  Start for SubSystem: '<Root>/__ExpFcn__2'
   *  Start for SubSystem: '<Root>/__ExpFcn__3'
   *  Start for SubSystem: '<Root>/__ExpFcn__4'
   */
  task_ts1_Start();
  task_ts3_Start();
}

/*
 * File trailer for Real-Time Workshop generated code.
 *
 * [EOF]
 */
