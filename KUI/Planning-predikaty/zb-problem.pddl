;; A very simple classical STRIPS domain
;; by Radek Marik, 2011.04.18

(define (problem PROBLEM1)
  (:domain PREPRAVA)
  (:objects Praha Plzen Liberec Kladno NewYork Stamford Trenton
            Lakewood Boston Newton Weymouth LasVegas Flagstaff
            Henderson Prescott Pariz Vernon Dreux Etampes Fontainebleau - mesto 
            Tuzky Papir Televize Avrim Michele Alex Jason - naklad
            KamA KamB KamC KamD KamE KamF KamG KamH KamI - vozidlo
	    OK312 DL456 - letadlo)
;; objekty 0000000000000000000000000000000000000000000000000000000000 
  (:init (and
    (ExCesta Praha Plzen)
    (ExCesta Plzen Praha)    
    (ExCesta Praha Liberec)
    (ExCesta Liberec Praha)
    (ExCesta Liberec Kladno)
    (ExCesta Kladno Liberec)
    (ExCesta NewYork Stamford)
    (ExCesta Stamford NewYork)
    (ExCesta NewYork Trenton)
    (ExCesta Trenton NewYork)
    (ExCesta Lakewood Trenton)
    (ExCesta Trenton Lakewood)
    (ExCesta Boston Newton)
    (ExCesta Newton Boston)
    (ExCesta Boston Weymouth)
    (ExCesta Weymouth Boston)
    (ExCesta Newton Weymouth)
    (ExCesta Weymouth Newton)
    (ExCesta LasVegas Flagstaff)
    (ExCesta Flagstaff LasVegas)
    (ExCesta LasVegas Henderson)
    (ExCesta Henderson LasVegas)
    (ExCesta Flagstaff Prescott)
    (ExCesta Prescott Flagstaff)
    (ExCesta Pariz Vernon) 
    (ExCesta Vernon Pariz)
    (ExCesta Vernon Dreux)
    (ExCesta Dreux Vernon)    
    (ExCesta Pariz Etampes)
    (ExCesta Etampes Pariz)
    (ExCesta Etampes Fontainebleau)
    (ExCesta Fontainebleau Etampes)
;; cesty 000000000000000000000000000000000000000000000000000000000000
    (ExSpoj Boston NewYork)
    (ExSpoj NewYork Boston)
    (ExSpoj NewYork LasVegas)
    (ExSpoj LasVegas NewYork)
    (ExSpoj NewYork Pariz)
    (ExSpoj Pariz NewYork)
    (ExSpoj NewYork Praha)
    (ExSpoj Praha NewYork)
    (ExSpoj Pariz Praha)
    (ExSpoj Praha Pariz)
;; spoje 000000000000000000000000000000000000000000000000000000000000
    (VozidloJe kamA Plzen)
    (VozidloJe kamB Kladno)
    (VozidloJe kamC Vernon)
    (VozidloJe kamD Fontainebleau)
    (VozidloJe kamE Prescott)
    (VozidloJe kamF Henderson)
    (VozidloJe kamG Stamford)
    (VozidloJe kamH Trenton)
    (VozidloJe kamI Weymouth)
;; Vozidla 0000000000000000000000000000000000000000000000000000000000
    (LetadloJe OK312 Pariz)
    (LetadloJe DL456 Boston)
;; Letadla 0000000000000000000000000000000000000000000000000000000000
    (NakladJe Tuzky Prescott)
    (NakladJe Papir Kladno)
    (NakladJe Televize Lakewood)
    (NakladJe Avrim Plzen)
    (NakladJe Michele Pariz)
    (NakladJe Alex Pariz)
    (NakladJe Jason LasVegas)
;; Naklady 0000000000000000000000000000000000000000000000000000000000
          )
  )
  
  (:goal (and 
    (NakladJe Tuzky Liberec)
    (NakladJe Papir Lakewood)
    (NakladJe Televize Pariz)
    (NakladJe Avrim Weymouth)
    (NakladJe Michele NewYork)
    (NakladJe Alex LasVegas)
    (NakladJe Jason Fontainebleau)
    )
  )
  

)