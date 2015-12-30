/*****************************************************************************
 * FILE: ecrobot_main.c
 *
 * MODEL: nxtway_gs_controller.mdl
 *
 * APP SUBSYSTEM: nxtway_app
 *
 * PLATFORM: OSEK
 *
 * DATE: 01-Mar-2011 16:53:46
 *
 * TOOL VERSION:
 *   Simulink: 7.6 (R2010b) 03-Aug-2010
 *   Real-Time Workshop: 7.6 (R2010b) 03-Aug-2010
 *   Real-Time Workshop Embedded Coder: 5.6 (R2010b) 03-Aug-2010
 *****************************************************************************/
/*============================================================================
 * RTW-EC generated functions for Function-Call Subsystems:
 *   task_init: exectuted at initialization
 *   task_ts1: executed at every 0.004[sec]
 *   task_ts2: executed at every 0.02[sec]
 *   task_ts3: executed at every 0.1[sec]
 *
 * RTW-EC generated model initialize function:
 *   nxtway_app_initialize
 *===========================================================================*/
#include "kernel.h"
#include "kernel_id.h"
#include "ecrobot_interface.h"
#include "nxtway_app.h"

/*============================================================================
 * OSEK declarations
 *===========================================================================*/
DeclareCounter(SysTimerCnt);
DeclareTask(OSEK_Task_ECRobotInitialize);
DeclareTask(OSEK_Task_task_ts1);
DeclareTask(OSEK_Task_task_ts2);
DeclareTask(OSEK_Task_task_ts3);
DeclareTask(OSEK_Task_ECRobotLCDMonitor);

/*============================================================================
 * Function: ecrobot_device_initialize
 */
void ecrobot_device_initialize(void)
{
  /* Initialize ECRobot used devices */
  ecrobot_init_sonar_sensor(NXT_PORT_S2);
  ecrobot_init_bt_slave("MATLAB");
}

/*============================================================================
 * Function: ecrobot_device_terminate
 */
void ecrobot_device_terminate(void)
{
  /* Terminate ECRobot used devices */
  ecrobot_set_motor_speed(NXT_PORT_B, 0);
  ecrobot_set_motor_speed(NXT_PORT_C, 0);
  ecrobot_term_sonar_sensor(NXT_PORT_S2);
  ecrobot_term_bt_connection();
}

/*============================================================================
 * Function: Invoked from a category 2 ISR
 */
void user_1ms_isr_type2(void)
{
  StatusType ercd;

  /* Increment System Timer Count */
  ercd = SignalCounter(SysTimerCnt);
  if (ercd != E_OK)
  {
    ShutdownOS(ercd);
  }
}

/*============================================================================
 * OSEK Hooks: empty functions
 */
void StartupHook(void){}

void ShutdownHook(StatusType ercd){}

void PreTaskHook(void){}

void PostTaskHook(void){}

void ErrorHook(StatusType ercd){}

/*============================================================================
 * Task: OSEK_Task_Initialize
 */
TASK(OSEK_Task_ECRobotInitialize)
{
  /* Call Initialize Function(s) */
  nxtway_app_initialize();
  task_init();

  TerminateTask();
}

/*============================================================================
 * Task: OSEK_Task_task_ts1
 */
TASK(OSEK_Task_task_ts1)
{
  /* Call task_ts1 every 4[msec] */
  task_ts1();

  TerminateTask();
}

/*============================================================================
 * Task: OSEK_Task_task_ts2
 */
TASK(OSEK_Task_task_ts2)
{
  /* Call task_ts2 every 20[msec] */
  task_ts2();

  TerminateTask();
}

/*============================================================================
 * Task: OSEK_Task_task_ts3
 */
TASK(OSEK_Task_task_ts3)
{
  /* Call task_ts3 every 100[msec] */
  task_ts3();

  TerminateTask();
}

/*============================================================================
 * Task: OSEK_Task_ECRobotLCDMonitor
 */
TASK(OSEK_Task_ECRobotLCDMonitor)
{
  /* Call this function every 500[msec] */
  ecrobot_adc_data_monitor("nxtway_app");

  TerminateTask();
}

/******************************** END OF FILE ********************************/
