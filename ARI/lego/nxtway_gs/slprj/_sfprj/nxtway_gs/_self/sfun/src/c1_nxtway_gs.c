/* Include files */

#include "blascompat32.h"
#include "nxtway_gs_sfun.h"
#include "c1_nxtway_gs.h"

/* Type Definitions */

/* Named Constants */

/* Variable Declarations */

/* Variable Definitions */

/* Function Declarations */
static void initialize_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance);
static void initialize_params_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct
  *chartInstance);
static void enable_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance);
static void disable_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance);
static const mxArray *get_sim_state_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct
  *chartInstance);
static void set_sim_state_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct
  *chartInstance, const mxArray *c1_st);
static void finalize_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance);
static void sf_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance);
static void init_script_number_translation(uint32_T c1_machineNumber, uint32_T
  c1_chartNumber);
static boolean_T c1_emlrt_marshallIn(SFc1_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c1_h, const char_T *c1_name);
static uint8_T c1_b_emlrt_marshallIn(SFc1_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c1_b_is_active_c1_nxtway_gs, const char_T *c1_name);
static void init_dsm_address_info(SFc1_nxtway_gsInstanceStruct *chartInstance);

/* Function Definitions */
static void initialize_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance)
{
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
  chartInstance->c1_is_active_c1_nxtway_gs = 0U;
}

static void initialize_params_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct
  *chartInstance)
{
  real_T c1_dv0[4];
  int32_T c1_i0;
  real_T c1_d0;
  sf_set_error_prefix_string(
    "Embedded MATLAB Runtime Error (chart): Error evaluating data 'D' in the parent workspace.\n");
  sf_mex_import("D", sf_mex_get_sfun_param(chartInstance->S, 0, 0), &c1_dv0, 0,
                0, 0U, 1, 0U, 1, 4);
  for (c1_i0 = 0; c1_i0 < 4; c1_i0 = c1_i0 + 1) {
    chartInstance->c1_D[c1_i0] = c1_dv0[c1_i0];
  }

  sf_set_error_prefix_string("Stateflow Runtime Error (chart): ");
  sf_set_error_prefix_string(
    "Embedded MATLAB Runtime Error (chart): Error evaluating data 'W' in the parent workspace.\n");
  sf_mex_import("W", sf_mex_get_sfun_param(chartInstance->S, 1, 0), &c1_d0, 0, 0,
                0U, 0, 0U, 0);
  chartInstance->c1_W = c1_d0;
  sf_set_error_prefix_string("Stateflow Runtime Error (chart): ");
}

static void enable_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance)
{
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
}

static void disable_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance)
{
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
}

static const mxArray *get_sim_state_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct
  *chartInstance)
{
  const mxArray *c1_st;
  const mxArray *c1_y = NULL;
  boolean_T c1_u;
  const mxArray *c1_b_y = NULL;
  uint8_T c1_b_u;
  const mxArray *c1_c_y = NULL;
  boolean_T *c1_h;
  c1_h = (boolean_T *)ssGetOutputPortSignal(chartInstance->S, 1);
  c1_st = NULL;
  c1_st = NULL;
  c1_y = NULL;
  sf_mex_assign(&c1_y, sf_mex_createcellarray(2));
  c1_u = *c1_h;
  c1_b_y = NULL;
  sf_mex_assign(&c1_b_y, sf_mex_create("y", &c1_u, 11, 0U, 0U, 0U, 0));
  sf_mex_setcell(c1_y, 0, c1_b_y);
  c1_b_u = chartInstance->c1_is_active_c1_nxtway_gs;
  c1_c_y = NULL;
  sf_mex_assign(&c1_c_y, sf_mex_create("y", &c1_b_u, 3, 0U, 0U, 0U, 0));
  sf_mex_setcell(c1_y, 1, c1_c_y);
  sf_mex_assign(&c1_st, c1_y);
  return c1_st;
}

static void set_sim_state_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct
  *chartInstance, const mxArray *c1_st)
{
  const mxArray *c1_u;
  boolean_T *c1_h;
  c1_h = (boolean_T *)ssGetOutputPortSignal(chartInstance->S, 1);
  c1_u = sf_mex_dup(c1_st);
  *c1_h = c1_emlrt_marshallIn(chartInstance, sf_mex_dup(sf_mex_getcell(c1_u, 0)),
    "h");
  chartInstance->c1_is_active_c1_nxtway_gs = c1_b_emlrt_marshallIn(chartInstance,
    sf_mex_dup(sf_mex_getcell(c1_u, 1)),
    "is_active_c1_nxtway_gs");
  sf_mex_destroy(&c1_u);
  sf_mex_destroy(&c1_st);
}

static void finalize_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance)
{
}

static void sf_c1_nxtway_gs(SFc1_nxtway_gsInstanceStruct *chartInstance)
{
  boolean_T *c1_h;
  c1_h = (boolean_T *)ssGetOutputPortSignal(chartInstance->S, 1);
  _sfTime_ = (real_T)ssGetT(chartInstance->S);

  /*  Detect if NXTway hits the wall */
  /*  Inputs: */
  /*    pos    : NXTway position ([z, x]) [cm] */
  /*    dir    : NXTway direction vector ([z, x]) [cm] */
  /*    map    : wall map */
  /*  Outputs: */
  /*    h      : wall hit flag */
  *c1_h = FALSE;

  /*  posh = zeros(6, 2); */
  /*  [rows, cols] = size(map); */
  /*  tmp1 = (D / 2) * dir; */
  /*  tmp2 = (W / 2) * [dir(2), -dir(1)]; */
  /*   */
  /*  posh(2, :) = pos + tmp1; */
  /*  posh(5, :) = pos - tmp1; */
  /*  posh(1, :) = posh(2, :) + tmp2; */
  /*  posh(6, :) = posh(5, :) + tmp2; */
  /*  posh(3, :) = posh(2, :) - tmp2; */
  /*  posh(4, :) = posh(5, :) - tmp2; */
  /*   */
  /*  for n = 1:6 */
  /*  	if posh(n, 1) <= 0 || posh(n, 1) >= rows ... */
  /*  			|| posh(n, 2) <= 0 || posh(n, 2) >= cols */
  /*  		% outside map */
  /*  		h = false; */
  /*  	else */
  /*  		% inside map */
  /*  		h = iswall(posh(n, :), map); */
  /*  		if h == true */
  /*  			errordlg('NXTway-GS Hits Wall !', 'Wall Hit Detection'); */
  /*  			break; */
  /*  		end */
  /*  	end */
  /*  end */
}

static void init_script_number_translation(uint32_T c1_machineNumber, uint32_T
  c1_chartNumber)
{
}

const mxArray *sf_c1_nxtway_gs_get_eml_resolved_functions_info(void)
{
  const mxArray *c1_nameCaptureInfo;
  c1_ResolvedFunctionInfo c1_info[1];
  c1_ResolvedFunctionInfo (*c1_b_info)[1];
  const mxArray *c1_m0 = NULL;
  c1_ResolvedFunctionInfo *c1_r0;
  c1_nameCaptureInfo = NULL;
  c1_nameCaptureInfo = NULL;
  c1_b_info = (c1_ResolvedFunctionInfo (*)[1])c1_info;
  (*c1_b_info)[0].context = "";
  (*c1_b_info)[0].name = "false";
  (*c1_b_info)[0].dominantType = "";
  (*c1_b_info)[0].resolved = "[B]false";
  (*c1_b_info)[0].fileLength = 0U;
  (*c1_b_info)[0].fileTime1 = 0U;
  (*c1_b_info)[0].fileTime2 = 0U;
  sf_mex_assign(&c1_m0, sf_mex_createstruct("nameCaptureInfo", 1, 1));
  c1_r0 = &c1_info[0];
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", (const char *)
    c1_r0->context, 15, 0U, 0U, 0U, 2, 1, strlen((const char *)
    c1_r0->context)), "context", "nameCaptureInfo", 0);
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", (const char *)
    c1_r0->name, 15, 0U, 0U, 0U, 2, 1, strlen((const char *)c1_r0
    ->name)), "name", "nameCaptureInfo", 0);
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", (const char *)
    c1_r0->dominantType, 15, 0U, 0U, 0U, 2, 1, strlen((const char
    *)c1_r0->dominantType)), "dominantType", "nameCaptureInfo", 0);
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", (const char *)
    c1_r0->resolved, 15, 0U, 0U, 0U, 2, 1, strlen((const char *)
    c1_r0->resolved)), "resolved", "nameCaptureInfo", 0);
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", &c1_r0->fileLength, 7,
    0U, 0U, 0U, 0), "fileLength", "nameCaptureInfo", 0);
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", &c1_r0->fileTime1, 7,
    0U, 0U, 0U, 0), "fileTime1", "nameCaptureInfo", 0);
  sf_mex_addfield(c1_m0, sf_mex_create("nameCaptureInfo", &c1_r0->fileTime2, 7,
    0U, 0U, 0U, 0), "fileTime2", "nameCaptureInfo", 0);
  sf_mex_assign(&c1_nameCaptureInfo, c1_m0);
  return c1_nameCaptureInfo;
}

static boolean_T c1_emlrt_marshallIn(SFc1_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c1_h, const char_T *c1_name)
{
  boolean_T c1_y;
  boolean_T c1_b0;
  sf_mex_import(c1_name, sf_mex_dup(c1_h), &c1_b0, 1, 11, 0U, 0, 0U, 0);
  c1_y = c1_b0;
  sf_mex_destroy(&c1_h);
  return c1_y;
}

static uint8_T c1_b_emlrt_marshallIn(SFc1_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c1_b_is_active_c1_nxtway_gs, const
  char_T *c1_name)
{
  uint8_T c1_y;
  uint8_T c1_u0;
  sf_mex_import(c1_name, sf_mex_dup(c1_b_is_active_c1_nxtway_gs), &c1_u0, 1, 3,
                0U, 0, 0U, 0);
  c1_y = c1_u0;
  sf_mex_destroy(&c1_b_is_active_c1_nxtway_gs);
  return c1_y;
}

static void init_dsm_address_info(SFc1_nxtway_gsInstanceStruct *chartInstance)
{
}

/* SFunction Glue Code */
void sf_c1_nxtway_gs_get_check_sum(mxArray *plhs[])
{
  ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(2550236683U);
  ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(2614180894U);
  ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(4139366285U);
  ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(611127147U);
}

mxArray *sf_c1_nxtway_gs_get_autoinheritance_info(void)
{
  const char *autoinheritanceFields[] = { "checksum", "inputs", "parameters",
    "outputs" };

  mxArray *mxAutoinheritanceInfo = mxCreateStructMatrix(1,1,4,
    autoinheritanceFields);

  {
    mxArray *mxChecksum = mxCreateDoubleMatrix(4,1,mxREAL);
    double *pr = mxGetPr(mxChecksum);
    pr[0] = (double)(2822834312U);
    pr[1] = (double)(1221185874U);
    pr[2] = (double)(3612693508U);
    pr[3] = (double)(3755989358U);
    mxSetField(mxAutoinheritanceInfo,0,"checksum",mxChecksum);
  }

  {
    const char *dataFields[] = { "size", "type", "complexity" };

    mxArray *mxData = mxCreateStructMatrix(1,3,3,dataFields);

    {
      mxArray *mxSize = mxCreateDoubleMatrix(1,2,mxREAL);
      double *pr = mxGetPr(mxSize);
      pr[0] = (double)(2);
      pr[1] = (double)(1);
      mxSetField(mxData,0,"size",mxSize);
    }

    {
      const char *typeFields[] = { "base", "fixpt" };

      mxArray *mxType = mxCreateStructMatrix(1,1,2,typeFields);
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(10));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,0,"type",mxType);
    }

    mxSetField(mxData,0,"complexity",mxCreateDoubleScalar(0));

    {
      mxArray *mxSize = mxCreateDoubleMatrix(1,2,mxREAL);
      double *pr = mxGetPr(mxSize);
      pr[0] = (double)(2);
      pr[1] = (double)(1);
      mxSetField(mxData,1,"size",mxSize);
    }

    {
      const char *typeFields[] = { "base", "fixpt" };

      mxArray *mxType = mxCreateStructMatrix(1,1,2,typeFields);
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(10));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,1,"type",mxType);
    }

    mxSetField(mxData,1,"complexity",mxCreateDoubleScalar(0));

    {
      mxArray *mxSize = mxCreateDoubleMatrix(1,2,mxREAL);
      double *pr = mxGetPr(mxSize);
      pr[0] = (double)(200);
      pr[1] = (double)(200);
      mxSetField(mxData,2,"size",mxSize);
    }

    {
      const char *typeFields[] = { "base", "fixpt" };

      mxArray *mxType = mxCreateStructMatrix(1,1,2,typeFields);
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(1));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,2,"type",mxType);
    }

    mxSetField(mxData,2,"complexity",mxCreateDoubleScalar(0));
    mxSetField(mxAutoinheritanceInfo,0,"inputs",mxData);
  }

  {
    const char *dataFields[] = { "size", "type", "complexity" };

    mxArray *mxData = mxCreateStructMatrix(1,2,3,dataFields);

    {
      mxArray *mxSize = mxCreateDoubleMatrix(1,2,mxREAL);
      double *pr = mxGetPr(mxSize);
      pr[0] = (double)(4);
      pr[1] = (double)(1);
      mxSetField(mxData,0,"size",mxSize);
    }

    {
      const char *typeFields[] = { "base", "fixpt" };

      mxArray *mxType = mxCreateStructMatrix(1,1,2,typeFields);
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(10));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,0,"type",mxType);
    }

    mxSetField(mxData,0,"complexity",mxCreateDoubleScalar(0));

    {
      mxArray *mxSize = mxCreateDoubleMatrix(1,2,mxREAL);
      double *pr = mxGetPr(mxSize);
      pr[0] = (double)(1);
      pr[1] = (double)(1);
      mxSetField(mxData,1,"size",mxSize);
    }

    {
      const char *typeFields[] = { "base", "fixpt" };

      mxArray *mxType = mxCreateStructMatrix(1,1,2,typeFields);
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(10));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,1,"type",mxType);
    }

    mxSetField(mxData,1,"complexity",mxCreateDoubleScalar(0));
    mxSetField(mxAutoinheritanceInfo,0,"parameters",mxData);
  }

  {
    const char *dataFields[] = { "size", "type", "complexity" };

    mxArray *mxData = mxCreateStructMatrix(1,1,3,dataFields);

    {
      mxArray *mxSize = mxCreateDoubleMatrix(1,2,mxREAL);
      double *pr = mxGetPr(mxSize);
      pr[0] = (double)(1);
      pr[1] = (double)(1);
      mxSetField(mxData,0,"size",mxSize);
    }

    {
      const char *typeFields[] = { "base", "fixpt" };

      mxArray *mxType = mxCreateStructMatrix(1,1,2,typeFields);
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(1));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,0,"type",mxType);
    }

    mxSetField(mxData,0,"complexity",mxCreateDoubleScalar(0));
    mxSetField(mxAutoinheritanceInfo,0,"outputs",mxData);
  }

  return(mxAutoinheritanceInfo);
}

static mxArray *sf_get_sim_state_info_c1_nxtway_gs(void)
{
  const char *infoFields[] = { "chartChecksum", "varInfo" };

  mxArray *mxInfo = mxCreateStructMatrix(1, 1, 2, infoFields);
  const char *infoEncStr[] = {
    "100 S1x2'type','srcId','name','auxInfo'{{M[1],M[5],T\"h\",},{M[8],M[0],T\"is_active_c1_nxtway_gs\",}}"
  };

  mxArray *mxVarInfo = sf_mex_decode_encoded_mx_struct_array(infoEncStr, 2, 10);
  mxArray *mxChecksum = mxCreateDoubleMatrix(1, 4, mxREAL);
  sf_c1_nxtway_gs_get_check_sum(&mxChecksum);
  mxSetField(mxInfo, 0, infoFields[0], mxChecksum);
  mxSetField(mxInfo, 0, infoFields[1], mxVarInfo);
  return mxInfo;
}

static void sf_opaque_initialize_c1_nxtway_gs(void *chartInstanceVar)
{
  initialize_params_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*)
    chartInstanceVar);
  initialize_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static void sf_opaque_enable_c1_nxtway_gs(void *chartInstanceVar)
{
  enable_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static void sf_opaque_disable_c1_nxtway_gs(void *chartInstanceVar)
{
  disable_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static void sf_opaque_gateway_c1_nxtway_gs(void *chartInstanceVar)
{
  sf_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static mxArray* sf_internal_get_sim_state_c1_nxtway_gs(SimStruct* S)
{
  ChartInfoStruct *chartInfo = (ChartInfoStruct*) ssGetUserData(S);
  mxArray *plhs[1] = { NULL };

  mxArray *prhs[4];
  int mxError = 0;
  prhs[0] = mxCreateString("chart_simctx_raw2high");
  prhs[1] = mxCreateDoubleScalar(ssGetSFuncBlockHandle(S));
  prhs[2] = (mxArray*) get_sim_state_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*)
    chartInfo->chartInstance);         /* raw sim ctx */
  prhs[3] = sf_get_sim_state_info_c1_nxtway_gs();/* state var info */
  mxError = sf_mex_call_matlab(1, plhs, 4, prhs, "sfprivate");
  mxDestroyArray(prhs[0]);
  mxDestroyArray(prhs[1]);
  mxDestroyArray(prhs[2]);
  mxDestroyArray(prhs[3]);
  if (mxError || plhs[0] == NULL) {
    sf_mex_error_message("Stateflow Internal Error: \nError calling 'chart_simctx_raw2high'.\n");
  }

  return plhs[0];
}

static void sf_internal_set_sim_state_c1_nxtway_gs(SimStruct* S, const mxArray
  *st)
{
  ChartInfoStruct *chartInfo = (ChartInfoStruct*) ssGetUserData(S);
  mxArray *plhs[1] = { NULL };

  mxArray *prhs[4];
  int mxError = 0;
  prhs[0] = mxCreateString("chart_simctx_high2raw");
  prhs[1] = mxCreateDoubleScalar(ssGetSFuncBlockHandle(S));
  prhs[2] = mxDuplicateArray(st);      /* high level simctx */
  prhs[3] = (mxArray*) sf_get_sim_state_info_c1_nxtway_gs();/* state var info */
  mxError = sf_mex_call_matlab(1, plhs, 4, prhs, "sfprivate");
  mxDestroyArray(prhs[0]);
  mxDestroyArray(prhs[1]);
  mxDestroyArray(prhs[2]);
  mxDestroyArray(prhs[3]);
  if (mxError || plhs[0] == NULL) {
    sf_mex_error_message("Stateflow Internal Error: \nError calling 'chart_simctx_high2raw'.\n");
  }

  set_sim_state_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*)
    chartInfo->chartInstance, mxDuplicateArray(plhs[0]));
  mxDestroyArray(plhs[0]);
}

static mxArray* sf_opaque_get_sim_state_c1_nxtway_gs(SimStruct* S)
{
  return sf_internal_get_sim_state_c1_nxtway_gs(S);
}

static void sf_opaque_set_sim_state_c1_nxtway_gs(SimStruct* S, const mxArray *st)
{
  sf_internal_set_sim_state_c1_nxtway_gs(S, st);
}

static void sf_opaque_terminate_c1_nxtway_gs(void *chartInstanceVar)
{
  if (chartInstanceVar!=NULL) {
    SimStruct *S = ((SFc1_nxtway_gsInstanceStruct*) chartInstanceVar)->S;
    if (sim_mode_is_rtw_gen(S) || sim_mode_is_external(S)) {
      sf_clear_rtw_identifier(S);
    }

    finalize_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*) chartInstanceVar);
    free((void *)chartInstanceVar);
    ssSetUserData(S,NULL);
  }
}

extern unsigned int sf_machine_global_initializer_called(void);
static void mdlProcessParameters_c1_nxtway_gs(SimStruct *S)
{
  int i;
  for (i=0;i<ssGetNumRunTimeParams(S);i++) {
    if (ssGetSFcnParamTunable(S,i)) {
      ssUpdateDlgParamAsRunTimeParam(S,i);
    }
  }

  if (sf_machine_global_initializer_called()) {
    initialize_params_c1_nxtway_gs((SFc1_nxtway_gsInstanceStruct*)
      (((ChartInfoStruct *)ssGetUserData(S))->chartInstance));
  }
}

static void mdlSetWorkWidths_c1_nxtway_gs(SimStruct *S)
{
  /* Actual parameters from chart:
     D W
   */
  const char_T *rtParamNames[] = { "p1", "p2" };

  ssSetNumRunTimeParams(S,ssGetSFcnParamsCount(S));

  /* registration for D*/
  ssRegDlgParamAsRunTimeParam(S, 0, 0, rtParamNames[0], SS_DOUBLE);

  /* registration for W*/
  ssRegDlgParamAsRunTimeParam(S, 1, 1, rtParamNames[1], SS_DOUBLE);
  if (sim_mode_is_rtw_gen(S) || sim_mode_is_external(S)) {
    int_T chartIsInlinable =
      (int_T)sf_is_chart_inlinable("nxtway_gs","nxtway_gs",1);
    ssSetStateflowIsInlinable(S,chartIsInlinable);
    ssSetRTWCG(S,sf_rtw_info_uint_prop("nxtway_gs","nxtway_gs",1,"RTWCG"));
    ssSetEnableFcnIsTrivial(S,1);
    ssSetDisableFcnIsTrivial(S,1);
    ssSetNotMultipleInlinable(S,sf_rtw_info_uint_prop("nxtway_gs","nxtway_gs",1,
      "gatewayCannotBeInlinedMultipleTimes"));
    if (chartIsInlinable) {
      ssSetInputPortOptimOpts(S, 0, SS_REUSABLE_AND_LOCAL);
      ssSetInputPortOptimOpts(S, 1, SS_REUSABLE_AND_LOCAL);
      ssSetInputPortOptimOpts(S, 2, SS_REUSABLE_AND_LOCAL);
      sf_mark_chart_expressionable_inputs(S,"nxtway_gs","nxtway_gs",1,3);
      sf_mark_chart_reusable_outputs(S,"nxtway_gs","nxtway_gs",1,1);
    }

    sf_set_rtw_dwork_info(S,"nxtway_gs","nxtway_gs",1);
    ssSetHasSubFunctions(S,!(chartIsInlinable));
    ssSetOptions(S,ssGetOptions(S)|SS_OPTION_WORKS_WITH_CODE_REUSE);
  }

  ssSetChecksum0(S,(1652737095U));
  ssSetChecksum1(S,(3347596066U));
  ssSetChecksum2(S,(2255272649U));
  ssSetChecksum3(S,(3344097651U));
  ssSetmdlDerivatives(S, NULL);
  ssSetExplicitFCSSCtrl(S,1);
}

static void mdlRTW_c1_nxtway_gs(SimStruct *S)
{
  if (sim_mode_is_rtw_gen(S)) {
    sf_write_symbol_mapping(S, "nxtway_gs", "nxtway_gs",1);
    ssWriteRTWStrParam(S, "StateflowChartType", "Embedded MATLAB");
  }
}

static void mdlStart_c1_nxtway_gs(SimStruct *S)
{
  SFc1_nxtway_gsInstanceStruct *chartInstance;
  chartInstance = (SFc1_nxtway_gsInstanceStruct *)malloc(sizeof
    (SFc1_nxtway_gsInstanceStruct));
  memset(chartInstance, 0, sizeof(SFc1_nxtway_gsInstanceStruct));
  if (chartInstance==NULL) {
    sf_mex_error_message("Could not allocate memory for chart instance.");
  }

  chartInstance->chartInfo.chartInstance = chartInstance;
  chartInstance->chartInfo.isEMLChart = 1;
  chartInstance->chartInfo.chartInitialized = 0;
  chartInstance->chartInfo.sFunctionGateway = sf_opaque_gateway_c1_nxtway_gs;
  chartInstance->chartInfo.initializeChart = sf_opaque_initialize_c1_nxtway_gs;
  chartInstance->chartInfo.terminateChart = sf_opaque_terminate_c1_nxtway_gs;
  chartInstance->chartInfo.enableChart = sf_opaque_enable_c1_nxtway_gs;
  chartInstance->chartInfo.disableChart = sf_opaque_disable_c1_nxtway_gs;
  chartInstance->chartInfo.getSimState = sf_opaque_get_sim_state_c1_nxtway_gs;
  chartInstance->chartInfo.setSimState = sf_opaque_set_sim_state_c1_nxtway_gs;
  chartInstance->chartInfo.getSimStateInfo = sf_get_sim_state_info_c1_nxtway_gs;
  chartInstance->chartInfo.zeroCrossings = NULL;
  chartInstance->chartInfo.outputs = NULL;
  chartInstance->chartInfo.derivatives = NULL;
  chartInstance->chartInfo.mdlRTW = mdlRTW_c1_nxtway_gs;
  chartInstance->chartInfo.mdlStart = mdlStart_c1_nxtway_gs;
  chartInstance->chartInfo.mdlSetWorkWidths = mdlSetWorkWidths_c1_nxtway_gs;
  chartInstance->chartInfo.extModeExec = NULL;
  chartInstance->chartInfo.restoreLastMajorStepConfiguration = NULL;
  chartInstance->chartInfo.restoreBeforeLastMajorStepConfiguration = NULL;
  chartInstance->chartInfo.storeCurrentConfiguration = NULL;
  chartInstance->S = S;
  ssSetUserData(S,(void *)(&(chartInstance->chartInfo)));/* register the chart instance with simstruct */
  if (!sim_mode_is_rtw_gen(S)) {
    init_dsm_address_info(chartInstance);
  }
}

void c1_nxtway_gs_method_dispatcher(SimStruct *S, int_T method, void *data)
{
  switch (method) {
   case SS_CALL_MDL_START:
    mdlStart_c1_nxtway_gs(S);
    break;

   case SS_CALL_MDL_SET_WORK_WIDTHS:
    mdlSetWorkWidths_c1_nxtway_gs(S);
    break;

   case SS_CALL_MDL_PROCESS_PARAMETERS:
    mdlProcessParameters_c1_nxtway_gs(S);
    break;

   default:
    /* Unhandled method */
    sf_mex_error_message("Stateflow Internal Error:\n"
                         "Error calling c1_nxtway_gs_method_dispatcher.\n"
                         "Can't handle method %d.\n", method);
    break;
  }
}
