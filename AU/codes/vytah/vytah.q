//This file was generated from (Academic) UPPAAL 4.0.11 (rev. 4492), February 2010

/*

*/
E<> deadlock

/*

*/
E<> exists (i : int[0,MAX_POCET_OSOB_VE_VYTAHU-1]) osoba(i).vytah_je_nebezpecny

/*

*/
E<> exists (i : int[0,POCET_PATER-1]) patro(i).vytah_je_nedostupny
