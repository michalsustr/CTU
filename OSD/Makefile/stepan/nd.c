#include "nd.h"

#include <math.h>
#include <stdlib.h>

int nd(int a) {
   // vypocet nejvetsiho delitele cisla a
   // nd je rovno 1, pokud je a prvocislo
   int ret;
   int min_sqrt;

   min_sqrt = sqrt(abs(a));
   for (ret=min_sqrt; ret>1; ret--) {
      if ((a % ret)==0) {
         break;
      }
   }
   return ret;
}

  

  
  
