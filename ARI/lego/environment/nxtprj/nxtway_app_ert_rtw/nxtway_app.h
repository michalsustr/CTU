/*
 * File: nxtway_app.h
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

#ifndef RTW_HEADER_nxtway_app_h_
#define RTW_HEADER_nxtway_app_h_
#ifndef nxtway_app_COMMON_INCLUDES_
# define nxtway_app_COMMON_INCLUDES_
#include <math.h>
#include "rtwtypes.h"
#endif                                 /* nxtway_app_COMMON_INCLUDES_ */

#include "nxtway_app_types.h"

/* Includes for objects with custom storage classes. */
#include "ecrobot_external_interface.h"

/* Macros for accessing real-time model data structure */
#ifndef rtmGetErrorStatus
# define rtmGetErrorStatus(rtm)        ((void*) 0)
#endif

#ifndef rtmSetErrorStatus
# define rtmSetErrorStatus(rtm, val)   ((void) 0)
#endif

#ifndef rtmGetStopRequested
# define rtmGetStopRequested(rtm)      ((void*) 0)
#endif

/* Block signals (auto storage) */
typedef struct {
  real32_T theta_diff;                 /* '<S33>/theta_diff' */
  int32_T RevolutionSensor_C;          /* '<S48>/Data Store Read' */
  uint8_T BluetoothRxRead[32];         /* '<S14>/Bluetooth Rx Read' */
} BlockIO;

/* Block states (auto storage) for system '<Root>' */
typedef struct {
  real32_T UnitDelay_DSTATE;           /* '<S28>/Unit Delay' */
  real32_T UnitDelay_DSTATE_j;         /* '<S34>/Unit Delay' */
  real32_T UnitDelay_DSTATE_e;         /* '<S35>/Unit Delay' */
  real32_T UnitDelay_DSTATE_h;         /* '<S38>/Unit Delay' */
  real32_T UnitDelay_DSTATE_hp;        /* '<S39>/Unit Delay' */
  real32_T UnitDelay_DSTATE_m;         /* '<S37>/Unit Delay' */
  real32_T gyro_offset;                /* '<S5>/Data Store Memory5' */
  real32_T battery;                    /* '<S5>/Data Store Memory7' */
  uint32_T start_time;                 /* '<S5>/Data Store Memory9' */
  uint8_T UnitDelay_DSTATE_k;          /* '<S16>/Unit Delay' */
  boolean_T UnitDelay_DSTATE_a;        /* '<S9>/Unit Delay' */
  boolean_T UnitDelay_DSTATE_c;        /* '<S32>/Unit Delay' */
  boolean_T flag_start;                /* '<S5>/Data Store Memory' */
  boolean_T flag_avoid;                /* '<S5>/Data Store Memory1' */
  boolean_T flag_mode;                 /* '<S5>/Data Store Memory3' */
  boolean_T flag_auto;                 /* '<S5>/Data Store Memory4' */
} D_Work;

/* Constant parameters (auto storage) */
typedef struct {
  /* Computed Parameter: FeedbackGain_Gain
   * Referenced by: '<S17>/Feedback Gain'
   */
  real32_T FeedbackGain_Gain[4];
} ConstParam;

/* Block signals (auto storage) */
extern BlockIO rtB;

/* Block states (auto storage) */
extern D_Work rtDWork;

/* Constant parameters (auto storage) */
extern const ConstParam rtConstP;

/* Model entry point functions */
extern void nxtway_app_initialize(void);
extern void task_ts3(void);
extern void task_ts2(void);
extern void task_ts1(void);
extern void task_init(void);

/*-
 * The generated code includes comments that allow you to trace directly
 * back to the appropriate location in the model.  The basic format
 * is <system>/block_name, where system is the system number (uniquely
 * assigned by Simulink) and block_name is the name of the block.
 *
 * Note that this particular code originates from a subsystem build,
 * and has its own system numbers different from the parent model.
 * Refer to the system hierarchy for this subsystem below, and use the
 * MATLAB hilite_system command to trace the generated code back
 * to the parent model.  For example,
 *
 * hilite_system('nxtway_gs_controller/nxtway_app')    - opens subsystem nxtway_gs_controller/nxtway_app
 * hilite_system('nxtway_gs_controller/nxtway_app/Kp') - opens and selects block Kp
 *
 * Here is the system hierarchy for this model
 *
 * '<Root>' : nxtway_gs_controller
 * '<S5>'   : nxtway_gs_controller/nxtway_app
 * '<S6>'   : nxtway_gs_controller/nxtway_app/task_init
 * '<S7>'   : nxtway_gs_controller/nxtway_app/task_ts1
 * '<S8>'   : nxtway_gs_controller/nxtway_app/task_ts2
 * '<S9>'   : nxtway_gs_controller/nxtway_app/task_ts3
 * '<S10>'  : nxtway_gs_controller/nxtway_app/task_init/Battery Voltage Read
 * '<S11>'  : nxtway_gs_controller/nxtway_app/task_init/Gyro Sensor Read
 * '<S12>'  : nxtway_gs_controller/nxtway_app/task_init/Manual Switch
 * '<S13>'  : nxtway_gs_controller/nxtway_app/task_init/System Clock Read
 * '<S14>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control
 * '<S15>'  : nxtway_gs_controller/nxtway_app/task_ts1/Gyro Calibration
 * '<S16>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Cal flag_log
 * '<S17>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller
 * '<S18>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Data Logging
 * '<S19>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Gyro Sensor Read
 * '<S20>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Make Command
 * '<S21>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Revolution Sensor Read
 * '<S22>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Revolution Sensor Read1
 * '<S23>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Servo Motor Write
 * '<S24>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Servo Motor Write1
 * '<S25>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal PWM
 * '<S26>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal Reference
 * '<S27>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal x1
 * '<S28>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Discrete Integrator (forward euler)
 * '<S29>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal PWM/Cal theta_diff
 * '<S30>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal PWM/Cal vol_max
 * '<S31>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal PWM/Friction Compensator
 * '<S32>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal PWM/Cal theta_diff/Detect Flag Falling
 * '<S33>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal PWM/Cal theta_diff/Set theta_offset
 * '<S34>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal Reference/Discrete Integrator (forward euler)
 * '<S35>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal Reference/Low Path Filter
 * '<S36>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal x1/Cal gyro_offset
 * '<S37>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal x1/Discrete Derivative (backward difference)
 * '<S38>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal x1/Discrete Integrator (forward euler)
 * '<S39>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Controller/Cal x1/Low Path Filter
 * '<S40>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Data Logging/Scaling
 * '<S41>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Make Command/Cal Command
 * '<S42>'  : nxtway_gs_controller/nxtway_app/task_ts1/Balance & Drive Control/Make Command/Get Command
 * '<S43>'  : nxtway_gs_controller/nxtway_app/task_ts1/Gyro Calibration/Gyro Sensor Read
 * '<S44>'  : nxtway_gs_controller/nxtway_app/task_ts2/Autonomous Drive
 * '<S45>'  : nxtway_gs_controller/nxtway_app/task_ts2/Remote Control Drive
 * '<S46>'  : nxtway_gs_controller/nxtway_app/task_ts2/Autonomous Drive/Forward Run
 * '<S47>'  : nxtway_gs_controller/nxtway_app/task_ts2/Autonomous Drive/Rotate Right
 * '<S48>'  : nxtway_gs_controller/nxtway_app/task_ts2/Autonomous Drive/Forward Run/Revolution Sensor Read
 * '<S49>'  : nxtway_gs_controller/nxtway_app/task_ts2/Autonomous Drive/Forward Run/Ultrasonic Sensor Read
 * '<S50>'  : nxtway_gs_controller/nxtway_app/task_ts2/Autonomous Drive/Rotate Right/Revolution Sensor Read
 * '<S51>'  : nxtway_gs_controller/nxtway_app/task_ts2/Remote Control Drive/Ultrasonic Sensor Read
 * '<S52>'  : nxtway_gs_controller/nxtway_app/task_ts3/Battery Voltage Read
 * '<S53>'  : nxtway_gs_controller/nxtway_app/task_ts3/Make Sound
 * '<S54>'  : nxtway_gs_controller/nxtway_app/task_ts3/System Clock Read
 */
#endif                                 /* RTW_HEADER_nxtway_app_h_ */

/*
 * File trailer for Real-Time Workshop generated code.
 *
 * [EOF]
 */
