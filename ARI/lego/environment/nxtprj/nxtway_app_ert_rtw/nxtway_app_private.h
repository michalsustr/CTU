/*
 * File: nxtway_app_private.h
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

#ifndef RTW_HEADER_nxtway_app_private_h_
#define RTW_HEADER_nxtway_app_private_h_
#include "rtwtypes.h"
#include "ecrobot_external_interface.h"
#include "ecrobot_external_interface.h"
#include "ecrobot_external_interface.h"
#ifndef __RTWTYPES_H__
#error This file requires rtwtypes.h to be included
#else
#ifdef TMWTYPES_PREVIOUSLY_INCLUDED
#error This file requires rtwtypes.h to be included before tmwtypes.h
#else

/* Check for inclusion of an incorrect version of rtwtypes.h */
#ifndef RTWTYPES_ID_C08S16I32L32N32F1
#error This code was generated with a different "rtwtypes.h" than the file included
#endif                                 /* RTWTYPES_ID_C08S16I32L32N32F1 */
#endif                                 /* TMWTYPES_PREVIOUSLY_INCLUDED */
#endif                                 /* __RTWTYPES_H__ */

extern void task_init(void);
extern void task_ts1_Start(void);
extern void task_ts1(void);
extern void task_ts2(void);
extern void task_ts3_Start(void);
extern void task_ts3(void);

#endif                                 /* RTW_HEADER_nxtway_app_private_h_ */

/*
 * File trailer for Real-Time Workshop generated code.
 *
 * [EOF]
 */
