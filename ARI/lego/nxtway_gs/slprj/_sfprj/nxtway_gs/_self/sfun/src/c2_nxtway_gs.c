/* Include files */

#include "blascompat32.h"
#include "nxtway_gs_sfun.h"
#include "c2_nxtway_gs.h"
#include "mwmathutil.h"

/* Type Definitions */

/* Named Constants */

/* Variable Declarations */

/* Variable Definitions */

/* Function Declarations */
static void initialize_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance);
static void initialize_params_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct
  *chartInstance);
static void enable_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance);
static void disable_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance);
static const mxArray *get_sim_state_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct
  *chartInstance);
static void set_sim_state_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct
  *chartInstance, const mxArray *c2_st);
static void finalize_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance);
static void sf_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance);
static void init_script_number_translation(uint32_T c2_machineNumber, uint32_T
  c2_chartNumber);
static void c2_info_helper(c2_ResolvedFunctionInfo c2_info[22]);
static real_T c2_emlrt_marshallIn(SFc2_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c2_d, const char_T *c2_name);
static uint8_T c2_b_emlrt_marshallIn(SFc2_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c2_b_is_active_c2_nxtway_gs, const char_T *c2_name);
static void init_dsm_address_info(SFc2_nxtway_gsInstanceStruct *chartInstance);

/* Function Definitions */
static void initialize_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance)
{
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
  chartInstance->c2_is_active_c2_nxtway_gs = 0U;
}

static void initialize_params_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct
  *chartInstance)
{
}

static void enable_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance)
{
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
}

static void disable_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance)
{
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
}

static const mxArray *get_sim_state_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct
  *chartInstance)
{
  const mxArray *c2_st;
  const mxArray *c2_y = NULL;
  real_T c2_u;
  const mxArray *c2_b_y = NULL;
  uint8_T c2_b_u;
  const mxArray *c2_c_y = NULL;
  real_T *c2_d;
  c2_d = (real_T *)ssGetOutputPortSignal(chartInstance->S, 1);
  c2_st = NULL;
  c2_st = NULL;
  c2_y = NULL;
  sf_mex_assign(&c2_y, sf_mex_createcellarray(2));
  c2_u = *c2_d;
  c2_b_y = NULL;
  sf_mex_assign(&c2_b_y, sf_mex_create("y", &c2_u, 0, 0U, 0U, 0U, 0));
  sf_mex_setcell(c2_y, 0, c2_b_y);
  c2_b_u = chartInstance->c2_is_active_c2_nxtway_gs;
  c2_c_y = NULL;
  sf_mex_assign(&c2_c_y, sf_mex_create("y", &c2_b_u, 3, 0U, 0U, 0U, 0));
  sf_mex_setcell(c2_y, 1, c2_c_y);
  sf_mex_assign(&c2_st, c2_y);
  return c2_st;
}

static void set_sim_state_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct
  *chartInstance, const mxArray *c2_st)
{
  const mxArray *c2_u;
  real_T *c2_d;
  c2_d = (real_T *)ssGetOutputPortSignal(chartInstance->S, 1);
  c2_u = sf_mex_dup(c2_st);
  *c2_d = c2_emlrt_marshallIn(chartInstance, sf_mex_dup(sf_mex_getcell(c2_u, 0)),
    "d");
  chartInstance->c2_is_active_c2_nxtway_gs = c2_b_emlrt_marshallIn(chartInstance,
    sf_mex_dup(sf_mex_getcell(c2_u, 1)),
    "is_active_c2_nxtway_gs");
  sf_mex_destroy(&c2_u);
  sf_mex_destroy(&c2_st);
}

static void finalize_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance)
{
}

static void sf_c2_nxtway_gs(SFc2_nxtway_gsInstanceStruct *chartInstance)
{
  int32_T c2_previousEvent;
  int32_T c2_d;
  real_T c2_pos[2];
  real_T c2_dir[2];
  boolean_T c2_map[40000];
  boolean_T c2_exitg1;
  int32_T c2_i0;
  real_T c2_posd[2];
  boolean_T c2_guard1 = FALSE;
  real_T c2_iz;
  real_T c2_ix;
  boolean_T c2_flag;
  real_T *c2_b_d;
  boolean_T (*c2_b_map)[40000];
  real_T (*c2_b_pos)[2];
  real_T (*c2_b_dir)[2];
  c2_b_map = (boolean_T (*)[40000])ssGetInputPortSignal(chartInstance->S, 2);
  c2_b_d = (real_T *)ssGetOutputPortSignal(chartInstance->S, 1);
  c2_b_dir = (real_T (*)[2])ssGetInputPortSignal(chartInstance->S, 1);
  c2_b_pos = (real_T (*)[2])ssGetInputPortSignal(chartInstance->S, 0);
  _sfTime_ = (real_T)ssGetT(chartInstance->S);
  c2_previousEvent = _sfEvent_;
  _sfEvent_ = CALL_EVENT;
  for (c2_d = 0; c2_d < 2; c2_d = c2_d + 1) {
    c2_pos[c2_d] = (*c2_b_pos)[c2_d];
    c2_dir[c2_d] = (*c2_b_dir)[c2_d];
  }

  for (c2_d = 0; c2_d < 40000; c2_d = c2_d + 1) {
    c2_map[c2_d] = (*c2_b_map)[c2_d];
  }

  /*  Calcurate sonar value (distance) */
  /*  Inputs: */
  /*    pos    : NXTway position ([z, x]) [cm] */
  /*    dir    : NXTway direction vector ([z, x]) [cm] */
  /*    map    : wall map */
  /*  Outputs: */
  /*    d      : sonar value (distance) [cm] */
  c2_d = 1;
  c2_exitg1 = 0U;
  while ((c2_exitg1 == 0U) && (c2_d < 255)) {
    for (c2_i0 = 0; c2_i0 < 2; c2_i0 = c2_i0 + 1) {
      c2_posd[c2_i0] = c2_pos[c2_i0] + (real_T)c2_d * c2_dir[c2_i0];
    }

    c2_guard1 = FALSE;
    if ((c2_posd[0] <= 0.0) || (c2_posd[0] >= 200.0) || (c2_posd[1] <= 0.0) ||
        (c2_posd[1] >= 200.0)) {
      /*  outside map */
      c2_d = 255;
      c2_guard1 = TRUE;
    } else {
      /*  inside map */
      /*  Check the position is inside a wall area or on the border of it */
      /*  Inputs: */
      /*    pos    : position ([z, x]) [cm] */
      /*    map    : wall map */
      /*  Outputs: */
      /*    flag   : wall flag */
      c2_iz = muDoubleScalarCeil(c2_posd[0]);
      c2_ix = muDoubleScalarCeil(c2_posd[1]);
      if ((c2_posd[0] != c2_iz) && (c2_posd[1] != c2_ix)) {
        /*  inside a wall area */
        c2_flag = c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz, 1, 200) - 1) +
          200 * (sf_mex_lw_bounds_check((int32_T)c2_ix, 1, 200) - 1)];
      } else if (c2_posd[0] == c2_iz) {
        /*  on the border of a wall area (z = integer) */
        if (c2_posd[1] != c2_ix) {
          if (c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz, 1, 200) - 1) + 200 *
              (sf_mex_lw_bounds_check((int32_T)c2_ix, 1, 200) - 1)] ||
              c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz + 1, 1, 200) - 1) +
              200 * ((int32_T)c2_ix - 1)]) {
            c2_flag = TRUE;
          } else {
            c2_flag = FALSE;
          }
        } else {
          /*  on the corner of a wall area (x, z = integer) */
          if (c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz, 1, 200) - 1) + 200 *
              (sf_mex_lw_bounds_check((int32_T)c2_ix, 1, 200) - 1)] ||
              c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz + 1, 1, 200) - 1) +
              200 * ((int32_T)c2_ix - 1)] || c2_map[((int32_T)c2_iz - 1) + 200
              * (sf_mex_lw_bounds_check((int32_T)c2_ix + 1, 1, 200) - 1)] ||
              c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz + 1, 1, 200) - 1) +
              200 * (sf_mex_lw_bounds_check((int32_T)c2_ix + 1, 1, 200) - 1)]) {
            c2_flag = TRUE;
          } else {
            c2_flag = FALSE;
          }
        }
      } else {
        /*  on the border of a wall area (x = integer) */
        if (c2_map[(sf_mex_lw_bounds_check((int32_T)c2_iz, 1, 200) - 1) + 200 *
            (sf_mex_lw_bounds_check((int32_T)c2_ix, 1, 200) - 1)] ||
            c2_map[((int32_T)c2_iz - 1) + 200 * (sf_mex_lw_bounds_check((int32_T)
              c2_ix + 1, 1, 200) - 1)]) {
          c2_flag = TRUE;
        } else {
          c2_flag = FALSE;
        }
      }

      if ((int32_T)c2_flag == 1) {
        c2_exitg1 = 1U;
      } else {
        c2_d = c2_d + 1;
        c2_guard1 = TRUE;
      }
    }

    if (c2_guard1 == TRUE) {
      sf_mex_listen_for_ctrl_c(chartInstance->S);
    }
  }

  *c2_b_d = (real_T)c2_d;
  _sfEvent_ = c2_previousEvent;
}

static void init_script_number_translation(uint32_T c2_machineNumber, uint32_T
  c2_chartNumber)
{
}

const mxArray *sf_c2_nxtway_gs_get_eml_resolved_functions_info(void)
{
  const mxArray *c2_nameCaptureInfo;
  c2_ResolvedFunctionInfo c2_info[22];
  const mxArray *c2_m0 = NULL;
  int32_T c2_i1;
  c2_ResolvedFunctionInfo *c2_r0;
  c2_nameCaptureInfo = NULL;
  c2_nameCaptureInfo = NULL;
  c2_info_helper(c2_info);
  sf_mex_assign(&c2_m0, sf_mex_createstruct("nameCaptureInfo", 1, 22));
  for (c2_i1 = 0; c2_i1 < 22; c2_i1 = c2_i1 + 1) {
    c2_r0 = &c2_info[c2_i1];
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", (const char *)
      c2_r0->context, 15, 0U, 0U, 0U, 2, 1, strlen((const char *)
      c2_r0->context)), "context", "nameCaptureInfo", c2_i1);
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", (const char *)
      c2_r0->name, 15, 0U, 0U, 0U, 2, 1, strlen((const char *)c2_r0
      ->name)), "name", "nameCaptureInfo", c2_i1);
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", (const char *)
      c2_r0->dominantType, 15, 0U, 0U, 0U, 2, 1, strlen((const char
      *)c2_r0->dominantType)), "dominantType", "nameCaptureInfo", c2_i1);
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", (const char *)
      c2_r0->resolved, 15, 0U, 0U, 0U, 2, 1, strlen((const char *)
      c2_r0->resolved)), "resolved", "nameCaptureInfo", c2_i1);
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", &c2_r0->fileLength,
      7, 0U, 0U, 0U, 0), "fileLength", "nameCaptureInfo",
                    c2_i1);
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", &c2_r0->fileTime1, 7,
      0U, 0U, 0U, 0), "fileTime1", "nameCaptureInfo", c2_i1);
    sf_mex_addfield(c2_m0, sf_mex_create("nameCaptureInfo", &c2_r0->fileTime2, 7,
      0U, 0U, 0U, 0), "fileTime2", "nameCaptureInfo", c2_i1);
  }

  sf_mex_assign(&c2_nameCaptureInfo, c2_m0);
  return c2_nameCaptureInfo;
}

static void c2_info_helper(c2_ResolvedFunctionInfo c2_info[22])
{
  c2_info[0].context = "";
  c2_info[0].name = "size";
  c2_info[0].dominantType = "logical";
  c2_info[0].resolved = "[B]size";
  c2_info[0].fileLength = 0U;
  c2_info[0].fileTime1 = 0U;
  c2_info[0].fileTime2 = 0U;
  c2_info[1].context = "";
  c2_info[1].name = "lt";
  c2_info[1].dominantType = "double";
  c2_info[1].resolved = "[B]lt";
  c2_info[1].fileLength = 0U;
  c2_info[1].fileTime1 = 0U;
  c2_info[1].fileTime2 = 0U;
  c2_info[2].context = "";
  c2_info[2].name = "mtimes";
  c2_info[2].dominantType = "double";
  c2_info[2].resolved = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[2].fileLength = 3425U;
  c2_info[2].fileTime1 = 1250672766U;
  c2_info[2].fileTime2 = 0U;
  c2_info[3].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[3].name = "nargin";
  c2_info[3].dominantType = "";
  c2_info[3].resolved = "[B]nargin";
  c2_info[3].fileLength = 0U;
  c2_info[3].fileTime1 = 0U;
  c2_info[3].fileTime2 = 0U;
  c2_info[4].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[4].name = "gt";
  c2_info[4].dominantType = "double";
  c2_info[4].resolved = "[B]gt";
  c2_info[4].fileLength = 0U;
  c2_info[4].fileTime1 = 0U;
  c2_info[4].fileTime2 = 0U;
  c2_info[5].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[5].name = "isa";
  c2_info[5].dominantType = "double";
  c2_info[5].resolved = "[B]isa";
  c2_info[5].fileLength = 0U;
  c2_info[5].fileTime1 = 0U;
  c2_info[5].fileTime2 = 0U;
  c2_info[6].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[6].name = "isinteger";
  c2_info[6].dominantType = "double";
  c2_info[6].resolved = "[B]isinteger";
  c2_info[6].fileLength = 0U;
  c2_info[6].fileTime1 = 0U;
  c2_info[6].fileTime2 = 0U;
  c2_info[7].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[7].name = "isscalar";
  c2_info[7].dominantType = "double";
  c2_info[7].resolved = "[B]isscalar";
  c2_info[7].fileLength = 0U;
  c2_info[7].fileTime1 = 0U;
  c2_info[7].fileTime2 = 0U;
  c2_info[8].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[8].name = "strcmp";
  c2_info[8].dominantType = "char";
  c2_info[8].resolved = "[B]strcmp";
  c2_info[8].fileLength = 0U;
  c2_info[8].fileTime1 = 0U;
  c2_info[8].fileTime2 = 0U;
  c2_info[9].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[9].name = "eq";
  c2_info[9].dominantType = "double";
  c2_info[9].resolved = "[B]eq";
  c2_info[9].fileLength = 0U;
  c2_info[9].fileTime1 = 0U;
  c2_info[9].fileTime2 = 0U;
  c2_info[10].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[10].name = "class";
  c2_info[10].dominantType = "double";
  c2_info[10].resolved = "[B]class";
  c2_info[10].fileLength = 0U;
  c2_info[10].fileTime1 = 0U;
  c2_info[10].fileTime2 = 0U;
  c2_info[11].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/ops/mtimes.m";
  c2_info[11].name = "not";
  c2_info[11].dominantType = "logical";
  c2_info[11].resolved = "[B]not";
  c2_info[11].fileLength = 0U;
  c2_info[11].fileTime1 = 0U;
  c2_info[11].fileTime2 = 0U;
  c2_info[12].context = "";
  c2_info[12].name = "plus";
  c2_info[12].dominantType = "double";
  c2_info[12].resolved = "[B]plus";
  c2_info[12].fileLength = 0U;
  c2_info[12].fileTime1 = 0U;
  c2_info[12].fileTime2 = 0U;
  c2_info[13].context = "";
  c2_info[13].name = "le";
  c2_info[13].dominantType = "double";
  c2_info[13].resolved = "[B]le";
  c2_info[13].fileLength = 0U;
  c2_info[13].fileTime1 = 0U;
  c2_info[13].fileTime2 = 0U;
  c2_info[14].context = "";
  c2_info[14].name = "ge";
  c2_info[14].dominantType = "double";
  c2_info[14].resolved = "[B]ge";
  c2_info[14].fileLength = 0U;
  c2_info[14].fileTime1 = 0U;
  c2_info[14].fileTime2 = 0U;
  c2_info[15].context = "";
  c2_info[15].name = "iswall";
  c2_info[15].dominantType = "double";
  c2_info[15].resolved = "[]/data/Code/Matlab/ARI/lego/nxtway_gs/iswall.m";
  c2_info[15].fileLength = 721U;
  c2_info[15].fileTime1 = 1302858624U;
  c2_info[15].fileTime2 = 0U;
  c2_info[16].context = "[]/data/Code/Matlab/ARI/lego/nxtway_gs/iswall.m";
  c2_info[16].name = "ceil";
  c2_info[16].dominantType = "double";
  c2_info[16].resolved = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/elfun/ceil.m";
  c2_info[16].fileLength = 329U;
  c2_info[16].fileTime1 = 1203447950U;
  c2_info[16].fileTime2 = 0U;
  c2_info[17].context = "[ILX]$matlabroot$/toolbox/eml/lib/matlab/elfun/ceil.m";
  c2_info[17].name = "eml_scalar_ceil";
  c2_info[17].dominantType = "double";
  c2_info[17].resolved =
    "[ILX]$matlabroot$/toolbox/eml/lib/matlab/elfun/eml_scalar_ceil.m";
  c2_info[17].fileLength = 253U;
  c2_info[17].fileTime1 = 1203447980U;
  c2_info[17].fileTime2 = 0U;
  c2_info[18].context =
    "[ILX]$matlabroot$/toolbox/eml/lib/matlab/elfun/eml_scalar_ceil.m";
  c2_info[18].name = "isfloat";
  c2_info[18].dominantType = "double";
  c2_info[18].resolved = "[B]isfloat";
  c2_info[18].fileLength = 0U;
  c2_info[18].fileTime1 = 0U;
  c2_info[18].fileTime2 = 0U;
  c2_info[19].context =
    "[ILX]$matlabroot$/toolbox/eml/lib/matlab/elfun/eml_scalar_ceil.m";
  c2_info[19].name = "isreal";
  c2_info[19].dominantType = "double";
  c2_info[19].resolved = "[B]isreal";
  c2_info[19].fileLength = 0U;
  c2_info[19].fileTime1 = 0U;
  c2_info[19].fileTime2 = 0U;
  c2_info[20].context = "[]/data/Code/Matlab/ARI/lego/nxtway_gs/iswall.m";
  c2_info[20].name = "ne";
  c2_info[20].dominantType = "double";
  c2_info[20].resolved = "[B]ne";
  c2_info[20].fileLength = 0U;
  c2_info[20].fileTime1 = 0U;
  c2_info[20].fileTime2 = 0U;
  c2_info[21].context = "";
  c2_info[21].name = "true";
  c2_info[21].dominantType = "";
  c2_info[21].resolved = "[B]true";
  c2_info[21].fileLength = 0U;
  c2_info[21].fileTime1 = 0U;
  c2_info[21].fileTime2 = 0U;
}

static real_T c2_emlrt_marshallIn(SFc2_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c2_d, const char_T *c2_name)
{
  real_T c2_y;
  real_T c2_d0;
  sf_mex_import(c2_name, sf_mex_dup(c2_d), &c2_d0, 1, 0, 0U, 0, 0U, 0);
  c2_y = c2_d0;
  sf_mex_destroy(&c2_d);
  return c2_y;
}

static uint8_T c2_b_emlrt_marshallIn(SFc2_nxtway_gsInstanceStruct *chartInstance,
  const mxArray *c2_b_is_active_c2_nxtway_gs, const
  char_T *c2_name)
{
  uint8_T c2_y;
  uint8_T c2_u0;
  sf_mex_import(c2_name, sf_mex_dup(c2_b_is_active_c2_nxtway_gs), &c2_u0, 1, 3,
                0U, 0, 0U, 0);
  c2_y = c2_u0;
  sf_mex_destroy(&c2_b_is_active_c2_nxtway_gs);
  return c2_y;
}

static void init_dsm_address_info(SFc2_nxtway_gsInstanceStruct *chartInstance)
{
}

/* SFunction Glue Code */
void sf_c2_nxtway_gs_get_check_sum(mxArray *plhs[])
{
  ((real_T *)mxGetPr((plhs[0])))[0] = (real_T)(4274852456U);
  ((real_T *)mxGetPr((plhs[0])))[1] = (real_T)(1680815973U);
  ((real_T *)mxGetPr((plhs[0])))[2] = (real_T)(3640803774U);
  ((real_T *)mxGetPr((plhs[0])))[3] = (real_T)(715672711U);
}

mxArray *sf_c2_nxtway_gs_get_autoinheritance_info(void)
{
  const char *autoinheritanceFields[] = { "checksum", "inputs", "parameters",
    "outputs" };

  mxArray *mxAutoinheritanceInfo = mxCreateStructMatrix(1,1,4,
    autoinheritanceFields);

  {
    mxArray *mxChecksum = mxCreateDoubleMatrix(4,1,mxREAL);
    double *pr = mxGetPr(mxChecksum);
    pr[0] = (double)(1987688138U);
    pr[1] = (double)(2407448300U);
    pr[2] = (double)(3929166339U);
    pr[3] = (double)(3195207749U);
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
    mxSetField(mxAutoinheritanceInfo,0,"parameters",mxCreateDoubleMatrix(0,0,
                mxREAL));
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
      mxSetField(mxType,0,"base",mxCreateDoubleScalar(10));
      mxSetField(mxType,0,"fixpt",mxCreateDoubleMatrix(0,0,mxREAL));
      mxSetField(mxData,0,"type",mxType);
    }

    mxSetField(mxData,0,"complexity",mxCreateDoubleScalar(0));
    mxSetField(mxAutoinheritanceInfo,0,"outputs",mxData);
  }

  return(mxAutoinheritanceInfo);
}

static mxArray *sf_get_sim_state_info_c2_nxtway_gs(void)
{
  const char *infoFields[] = { "chartChecksum", "varInfo" };

  mxArray *mxInfo = mxCreateStructMatrix(1, 1, 2, infoFields);
  const char *infoEncStr[] = {
    "100 S1x2'type','srcId','name','auxInfo'{{M[1],M[6],T\"d\",},{M[8],M[0],T\"is_active_c2_nxtway_gs\",}}"
  };

  mxArray *mxVarInfo = sf_mex_decode_encoded_mx_struct_array(infoEncStr, 2, 10);
  mxArray *mxChecksum = mxCreateDoubleMatrix(1, 4, mxREAL);
  sf_c2_nxtway_gs_get_check_sum(&mxChecksum);
  mxSetField(mxInfo, 0, infoFields[0], mxChecksum);
  mxSetField(mxInfo, 0, infoFields[1], mxVarInfo);
  return mxInfo;
}

static void sf_opaque_initialize_c2_nxtway_gs(void *chartInstanceVar)
{
  initialize_params_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*)
    chartInstanceVar);
  initialize_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static void sf_opaque_enable_c2_nxtway_gs(void *chartInstanceVar)
{
  enable_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static void sf_opaque_disable_c2_nxtway_gs(void *chartInstanceVar)
{
  disable_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static void sf_opaque_gateway_c2_nxtway_gs(void *chartInstanceVar)
{
  sf_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*) chartInstanceVar);
}

static mxArray* sf_internal_get_sim_state_c2_nxtway_gs(SimStruct* S)
{
  ChartInfoStruct *chartInfo = (ChartInfoStruct*) ssGetUserData(S);
  mxArray *plhs[1] = { NULL };

  mxArray *prhs[4];
  int mxError = 0;
  prhs[0] = mxCreateString("chart_simctx_raw2high");
  prhs[1] = mxCreateDoubleScalar(ssGetSFuncBlockHandle(S));
  prhs[2] = (mxArray*) get_sim_state_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*)
    chartInfo->chartInstance);         /* raw sim ctx */
  prhs[3] = sf_get_sim_state_info_c2_nxtway_gs();/* state var info */
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

static void sf_internal_set_sim_state_c2_nxtway_gs(SimStruct* S, const mxArray
  *st)
{
  ChartInfoStruct *chartInfo = (ChartInfoStruct*) ssGetUserData(S);
  mxArray *plhs[1] = { NULL };

  mxArray *prhs[4];
  int mxError = 0;
  prhs[0] = mxCreateString("chart_simctx_high2raw");
  prhs[1] = mxCreateDoubleScalar(ssGetSFuncBlockHandle(S));
  prhs[2] = mxDuplicateArray(st);      /* high level simctx */
  prhs[3] = (mxArray*) sf_get_sim_state_info_c2_nxtway_gs();/* state var info */
  mxError = sf_mex_call_matlab(1, plhs, 4, prhs, "sfprivate");
  mxDestroyArray(prhs[0]);
  mxDestroyArray(prhs[1]);
  mxDestroyArray(prhs[2]);
  mxDestroyArray(prhs[3]);
  if (mxError || plhs[0] == NULL) {
    sf_mex_error_message("Stateflow Internal Error: \nError calling 'chart_simctx_high2raw'.\n");
  }

  set_sim_state_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*)
    chartInfo->chartInstance, mxDuplicateArray(plhs[0]));
  mxDestroyArray(plhs[0]);
}

static mxArray* sf_opaque_get_sim_state_c2_nxtway_gs(SimStruct* S)
{
  return sf_internal_get_sim_state_c2_nxtway_gs(S);
}

static void sf_opaque_set_sim_state_c2_nxtway_gs(SimStruct* S, const mxArray *st)
{
  sf_internal_set_sim_state_c2_nxtway_gs(S, st);
}

static void sf_opaque_terminate_c2_nxtway_gs(void *chartInstanceVar)
{
  if (chartInstanceVar!=NULL) {
    SimStruct *S = ((SFc2_nxtway_gsInstanceStruct*) chartInstanceVar)->S;
    if (sim_mode_is_rtw_gen(S) || sim_mode_is_external(S)) {
      sf_clear_rtw_identifier(S);
    }

    finalize_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*) chartInstanceVar);
    free((void *)chartInstanceVar);
    ssSetUserData(S,NULL);
  }
}

extern unsigned int sf_machine_global_initializer_called(void);
static void mdlProcessParameters_c2_nxtway_gs(SimStruct *S)
{
  int i;
  for (i=0;i<ssGetNumRunTimeParams(S);i++) {
    if (ssGetSFcnParamTunable(S,i)) {
      ssUpdateDlgParamAsRunTimeParam(S,i);
    }
  }

  if (sf_machine_global_initializer_called()) {
    initialize_params_c2_nxtway_gs((SFc2_nxtway_gsInstanceStruct*)
      (((ChartInfoStruct *)ssGetUserData(S))->chartInstance));
  }
}

static void mdlSetWorkWidths_c2_nxtway_gs(SimStruct *S)
{
  if (sim_mode_is_rtw_gen(S) || sim_mode_is_external(S)) {
    int_T chartIsInlinable =
      (int_T)sf_is_chart_inlinable("nxtway_gs","nxtway_gs",2);
    ssSetStateflowIsInlinable(S,chartIsInlinable);
    ssSetRTWCG(S,sf_rtw_info_uint_prop("nxtway_gs","nxtway_gs",2,"RTWCG"));
    ssSetEnableFcnIsTrivial(S,1);
    ssSetDisableFcnIsTrivial(S,1);
    ssSetNotMultipleInlinable(S,sf_rtw_info_uint_prop("nxtway_gs","nxtway_gs",2,
      "gatewayCannotBeInlinedMultipleTimes"));
    if (chartIsInlinable) {
      ssSetInputPortOptimOpts(S, 0, SS_REUSABLE_AND_LOCAL);
      ssSetInputPortOptimOpts(S, 1, SS_REUSABLE_AND_LOCAL);
      ssSetInputPortOptimOpts(S, 2, SS_REUSABLE_AND_LOCAL);
      sf_mark_chart_expressionable_inputs(S,"nxtway_gs","nxtway_gs",2,3);
      sf_mark_chart_reusable_outputs(S,"nxtway_gs","nxtway_gs",2,1);
    }

    sf_set_rtw_dwork_info(S,"nxtway_gs","nxtway_gs",2);
    ssSetHasSubFunctions(S,!(chartIsInlinable));
    ssSetOptions(S,ssGetOptions(S)|SS_OPTION_WORKS_WITH_CODE_REUSE);
  }

  ssSetChecksum0(S,(648207343U));
  ssSetChecksum1(S,(2980120701U));
  ssSetChecksum2(S,(3679223737U));
  ssSetChecksum3(S,(975652U));
  ssSetmdlDerivatives(S, NULL);
  ssSetExplicitFCSSCtrl(S,1);
}

static void mdlRTW_c2_nxtway_gs(SimStruct *S)
{
  if (sim_mode_is_rtw_gen(S)) {
    sf_write_symbol_mapping(S, "nxtway_gs", "nxtway_gs",2);
    ssWriteRTWStrParam(S, "StateflowChartType", "Embedded MATLAB");
  }
}

static void mdlStart_c2_nxtway_gs(SimStruct *S)
{
  SFc2_nxtway_gsInstanceStruct *chartInstance;
  chartInstance = (SFc2_nxtway_gsInstanceStruct *)malloc(sizeof
    (SFc2_nxtway_gsInstanceStruct));
  memset(chartInstance, 0, sizeof(SFc2_nxtway_gsInstanceStruct));
  if (chartInstance==NULL) {
    sf_mex_error_message("Could not allocate memory for chart instance.");
  }

  chartInstance->chartInfo.chartInstance = chartInstance;
  chartInstance->chartInfo.isEMLChart = 1;
  chartInstance->chartInfo.chartInitialized = 0;
  chartInstance->chartInfo.sFunctionGateway = sf_opaque_gateway_c2_nxtway_gs;
  chartInstance->chartInfo.initializeChart = sf_opaque_initialize_c2_nxtway_gs;
  chartInstance->chartInfo.terminateChart = sf_opaque_terminate_c2_nxtway_gs;
  chartInstance->chartInfo.enableChart = sf_opaque_enable_c2_nxtway_gs;
  chartInstance->chartInfo.disableChart = sf_opaque_disable_c2_nxtway_gs;
  chartInstance->chartInfo.getSimState = sf_opaque_get_sim_state_c2_nxtway_gs;
  chartInstance->chartInfo.setSimState = sf_opaque_set_sim_state_c2_nxtway_gs;
  chartInstance->chartInfo.getSimStateInfo = sf_get_sim_state_info_c2_nxtway_gs;
  chartInstance->chartInfo.zeroCrossings = NULL;
  chartInstance->chartInfo.outputs = NULL;
  chartInstance->chartInfo.derivatives = NULL;
  chartInstance->chartInfo.mdlRTW = mdlRTW_c2_nxtway_gs;
  chartInstance->chartInfo.mdlStart = mdlStart_c2_nxtway_gs;
  chartInstance->chartInfo.mdlSetWorkWidths = mdlSetWorkWidths_c2_nxtway_gs;
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

void c2_nxtway_gs_method_dispatcher(SimStruct *S, int_T method, void *data)
{
  switch (method) {
   case SS_CALL_MDL_START:
    mdlStart_c2_nxtway_gs(S);
    break;

   case SS_CALL_MDL_SET_WORK_WIDTHS:
    mdlSetWorkWidths_c2_nxtway_gs(S);
    break;

   case SS_CALL_MDL_PROCESS_PARAMETERS:
    mdlProcessParameters_c2_nxtway_gs(S);
    break;

   default:
    /* Unhandled method */
    sf_mex_error_message("Stateflow Internal Error:\n"
                         "Error calling c2_nxtway_gs_method_dispatcher.\n"
                         "Can't handle method %d.\n", method);
    break;
  }
}
