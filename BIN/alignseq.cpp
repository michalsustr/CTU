// alignseq
// 
// Usage: see align.m
// Compile with mex alignseq.cpp
// 

#include <matrix.h>
#include <mex.h>   
#include <math.h>
#include <limits.h>
#include <stdint.h>

#define MIN(a,b) (((a)<(b))?(a):(b))
#define MAX(a,b) (((a)>(b))?(a):(b))

#define MAX_ALLOC_MEM 25000000

double *r, *p;
int gap, match, mismatch;

int d(int a, int b) {
   return r[a] == p[b] ? match : mismatch;
}


uint8_t dir=0;

/*
    a1 (corner)
    a2 (up)
    a3 (left)
*/
int max(int a1, int a2, int a3) {
                        int r = a1; dir = 1;
    if(a2 > a1 && a2 > r) { r = a2; dir = 2; }
    if(a3 > a2 && a3 > r) { r = a3; dir = 3; }
    return r;
}



void mexFunction(int nlhs, mxArray *plhs[], int nrhs, const mxArray *prhs[])
{
    int R, P;

    if(nrhs < 5) {
        mexErrMsgTxt("Not enough parameters");
        return;
    }
    
    // associate inputs
    r = mxGetPr(prhs[0]);
    p = mxGetPr(prhs[1]);

    gap = mxGetScalar(prhs[2]);
    match = mxGetScalar(prhs[3]);
    mismatch = mxGetScalar(prhs[4]);

    R = (int) mxGetM(prhs[0]);
    P = (int) mxGetM(prhs[1]);

    if(R*P > MAX_ALLOC_MEM) {
        mexErrMsgTxt("Too big input");
        return;
    }

    // associate outputs
    plhs[0] = mxCreateNumericMatrix(1,1, mxINT32_CLASS, 0);
    int *Dist = (int *) mxGetData(plhs[0]);


    // init values
    double D[R+1][P+1];
    for (int ri = 0; ri < 2; ++ri)
        for (int pi = 0; pi < P; ++pi)
            D[ri][pi] = DBL_MIN;

    uint8_t WAY[R][P];
    for (int ri = 0; ri < R; ++ri)
            for (int pi = 0; pi < P; ++pi)
                    WAY[ri][pi] = 0;
    
    
    // ---- initialize first plane ---
    // init corner
    D[0][0] = 0;
    WAY[0][0] = 0; // stay here

    // init first row
    for (int ip = 1; ip < P+1; ++ip) {
        D[0][ip] = gap + D[0][ip-1];
        WAY[0][ip] = 2;
    }
    // init first col
    for (int ir = 1; ir < R+1; ++ir) {
        D[ir][0] = gap + D[ir-1][0];
        WAY[ir][0] = 3;
    }
    
    // main computation
    for (int ir = 1; ir < R+1; ++ir) {
        // compute the rest of matrix
        for(int ip=1; ip < P+1; ++ip) {
            D[ir][ip] = max(
                D[ir-1][ip-1] + d(ir-1,ip-1),
                D[ir-1][ip] + gap,
                D[ir][ip-1] + gap
            );
            WAY[ir][ip] = dir;
        }
    }

    Dist[0] = (int) D[R][P];
   
    // trace way all the way to the top from the bottom corner
    int ir = R;
    int ip = P;
    int W[2*(R+P)];
    for (int i = 0; i < (R+P)*2; ++i)
    {
        W[i] = 0;
    }
    int Wi = 0;
    int doSearch = 1;
    int lastDir = -1;
    do {
        if(lastDir == 3) {
            W[Wi*2+0] = 0; 
        } else {
            W[Wi*2+0] = r[ir-1];
        }
        if(lastDir == 2) {
            W[Wi*2+1] = 0; 
        } else {
            W[Wi*2+1] = p[ip-1];
        }

        switch(WAY[ir][ip]) {
            case 0:
                doSearch = 0;
                break;
            case 1:
                lastDir = 1;
                ir--;
                ip--;
                break;
            case 2:
                lastDir = 2;
                ir--;
                break;
            case 3:
                lastDir = 3;
                ip--;
                break;
        }
        Wi++;
    } while(doSearch);

    // associate last output
    plhs[1] = mxCreateNumericMatrix(2,Wi-1, mxINT32_CLASS, 0);
    int *Wret = (int*) mxGetData(plhs[1]);

    // copy outputs
    int j = 0;
    for (int i = Wi-2; i >= 0; --i)
    {
        Wret[j*2+0] = W[i*2+0];
        Wret[j*2+1] = W[i*2+1];
        // printf("%f %f %f\n",W[i*3+0],W[i*3+1],W[i*3+2]);
        j++;
    }
}
