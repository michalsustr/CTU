#include <stdio.h>

int main(int /*argc*/, char **/*argv*/)
{
    FILE * f = fopen( "Makefile_r", "w" );
    int tc = 12000, ts = 20;
//    int tc = 5, ts = 10;
    if ( f )
    {

        for ( int i = 0; i < tc; ++i )
        {
            fprintf(f, "t%d:", i);
            for ( int j = i+1; j < i + ts; ++j )
                fprintf(f, " t%d", j);
            fprintf(f, "\n");
        }

        for ( int k = tc; k < tc+ts-1; ++k )
            fprintf(f, "t%d:\n", k);
        fclose(f);
    }
    return 0;
}
