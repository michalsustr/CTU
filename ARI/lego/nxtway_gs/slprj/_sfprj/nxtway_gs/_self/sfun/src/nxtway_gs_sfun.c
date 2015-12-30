/* Include files */

#include "nxtway_gs_sfun.h"
#include "c1_nxtway_gs.h"
#include "c2_nxtway_gs.h"

/* Type Definitions */

/* Named Constants */

/* Variable Declarations */

/* Variable Definitions */
int32_T _sfEvent_;
real_T _sfTime_;

/* Function Declarations */

/* Function Definitions */
void nxtway_gs_initializer(void)
{
  _sfEvent_ = CALL_EVENT;
}

void nxtway_gs_terminator(void)
{
}

/* SFunction Glue Code */
unsigned int sf_nxtway_gs_method_dispatcher(SimStruct *simstructPtr, unsigned
  int chartFileNumber, const char* specsCksum, int_T method, void *data)
{
  if (chartFileNumber==1) {
    c1_nxtway_gs_method_dispatcher(simstructPtr, method, data);
    return 1;
  }

  if (chartFileNumber==2) {
    c2_nxtway_gs_method_dispatcher(simstructPtr, method, data);
    return 1;
  }

  return 0;
}

unsigned int sf_nxtway_gs_process_check_sum_call( int nlhs, mxArray * plhs[],
  int nrhs, const mxArray * prhs[] )
{

#ifdef MATLAB_MEX_FILE

  char commandName[20];
  if (nrhs<1 || !mxIsChar(prhs[0]) )
    return 0;

  /* Possible call to get the checksum */
  mxGetString(prhs[0], commandName,sizeof(commandName)/sizeof(char));
  commandName[(sizeof(commandName)/sizeof(char)-1)] = '\0';
  if (strcmp(commandName,"sf_get_check_sum"))
    return 0;
  plhs[0] = mxCreateDoubleMatrix( 1,4,mxREAL);
  if (nrhs>1 && mxIsChar(prhs[1])) {
    mxGetString(prhs[1], commandName,sizeof(commandName)/sizeof(char));
    commandName[(sizeof(commandName)/sizeof(char)-1)] = '\0';
    if (!strcmp(commandName,"machine")) {
      ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(423227801U);
      ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(2361322088U);
      ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(15962461U);
      ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(3686329952U);
    } else if (!strcmp(commandName,"exportedFcn")) {
      ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(0U);
      ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(0U);
      ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(0U);
      ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(0U);
    } else if (!strcmp(commandName,"makefile")) {
      ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(3802036230U);
      ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(4064410007U);
      ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(44748966U);
      ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(326270580U);
    } else if (nrhs==3 && !strcmp(commandName,"chart")) {
      unsigned int chartFileNumber;
      chartFileNumber = (unsigned int)mxGetScalar(prhs[2]);
      switch (chartFileNumber) {
       case 1:
        {
          extern void sf_c1_nxtway_gs_get_check_sum(mxArray *plhs[]);
          sf_c1_nxtway_gs_get_check_sum(plhs);
          break;
        }

       case 2:
        {
          extern void sf_c2_nxtway_gs_get_check_sum(mxArray *plhs[]);
          sf_c2_nxtway_gs_get_check_sum(plhs);
          break;
        }

       default:
        ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(0.0);
        ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(0.0);
        ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(0.0);
        ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(0.0);
      }
    } else if (!strcmp(commandName,"target")) {
      ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(3034611889U);
      ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(2498606509U);
      ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(4149269935U);
      ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(3250596596U);
    } else {
      return 0;
    }
  } else {
    ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(1920202660U);
    ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(2791416337U);
    ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(1853333452U);
    ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(2277005027U);
  }

  return 1;

#else

  return 0;

#endif

}

unsigned int sf_nxtway_gs_autoinheritance_info( int nlhs, mxArray * plhs[], int
  nrhs, const mxArray * prhs[] )
{

#ifdef MATLAB_MEX_FILE

  char commandName[32];
  if (nrhs<2 || !mxIsChar(prhs[0]) )
    return 0;

  /* Possible call to get the autoinheritance_info */
  mxGetString(prhs[0], commandName,sizeof(commandName)/sizeof(char));
  commandName[(sizeof(commandName)/sizeof(char)-1)] = '\0';
  if (strcmp(commandName,"get_autoinheritance_info"))
    return 0;

  {
    unsigned int chartFileNumber;
    chartFileNumber = (unsigned int)mxGetScalar(prhs[1]);
    switch (chartFileNumber) {
     case 1:
      {
        extern mxArray *sf_c1_nxtway_gs_get_autoinheritance_info(void);
        plhs[0] = sf_c1_nxtway_gs_get_autoinheritance_info();
        break;
      }

     case 2:
      {
        extern mxArray *sf_c2_nxtway_gs_get_autoinheritance_info(void);
        plhs[0] = sf_c2_nxtway_gs_get_autoinheritance_info();
        break;
      }

     default:
      plhs[0] = mxCreateDoubleMatrix(0,0,mxREAL);
    }
  }

  return 1;

#else

  return 0;

#endif

}

unsigned int sf_nxtway_gs_get_eml_resolved_functions_info( int nlhs, mxArray *
  plhs[], int nrhs, const mxArray * prhs[] )
{

#ifdef MATLAB_MEX_FILE

  char commandName[64];
  if (nrhs<2 || !mxIsChar(prhs[0]))
    return 0;

  /* Possible call to get the get_eml_resolved_functions_info */
  mxGetString(prhs[0], commandName,sizeof(commandName)/sizeof(char));
  commandName[(sizeof(commandName)/sizeof(char)-1)] = '\0';
  if (strcmp(commandName,"get_eml_resolved_functions_info"))
    return 0;

  {
    unsigned int chartFileNumber;
    chartFileNumber = (unsigned int)mxGetScalar(prhs[1]);
    switch (chartFileNumber) {
     case 1:
      {
        extern const mxArray *sf_c1_nxtway_gs_get_eml_resolved_functions_info
          (void);
        mxArray *persistentMxArray = (mxArray *)
          sf_c1_nxtway_gs_get_eml_resolved_functions_info();
        plhs[0] = mxDuplicateArray(persistentMxArray);
        mxDestroyArray(persistentMxArray);
        break;
      }

     case 2:
      {
        extern const mxArray *sf_c2_nxtway_gs_get_eml_resolved_functions_info
          (void);
        mxArray *persistentMxArray = (mxArray *)
          sf_c2_nxtway_gs_get_eml_resolved_functions_info();
        plhs[0] = mxDuplicateArray(persistentMxArray);
        mxDestroyArray(persistentMxArray);
        break;
      }

     default:
      plhs[0] = mxCreateDoubleMatrix(0,0,mxREAL);
    }
  }

  return 1;

#else

  return 0;

#endif

}

void nxtway_gs_register_exported_symbols(SimStruct* S)
{
}
