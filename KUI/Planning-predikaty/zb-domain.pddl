;; A very simple classical STRIPS domain
;; by Zbynek Bambusek, 7.5.2011

(define (domain PREPRAVA)
  (:requirements :strips :typing)
  (:types mesto naklad vozidlo letadlo)
  (:predicates
    (ExCesta ?m1 ?m2 - mesto)
    (ExSpoj ?m1 ?m2 - mesto)
    (VozidloJe ?v - vozidlo ?m - mesto)
    (LetadloJe ?l - letadlo ?m - mesto)
    (NakladJe ?n - naklad ?m - mesto)
  )
  
  ;; prazdne pohyby 000000000000000000000000000000000000000000000000000000000000000
  (:action JED
    :parameters (?v - vozidlo ?m1 ?m2 - mesto)
    :precondition (and (VozidloJe ?v ?m1) (ExCesta ?m1 ?m2))
    :effect (and (VozidloJe ?v ?m2) (not (VozidloJe ?v ?m1)))
  )

  (:action LET
    :parameters (?l - letadlo ?m1 ?m2 - mesto)
    :precondition (and (LetadloJe ?l ?m1) (ExSpoj ?m1 ?m2))
    :effect (and (LetadloJe ?l ?m2) (not (LetadloJe ?l ?m1)))
  )

  ;; plne pohyby 000000000000000000000000000000000000000000000000000000000000000000
  (:action PREVEZ
    :parameters (?v - vozidlo ?n - naklad ?m1 ?m2 - mesto)
    :precondition (and (VozidloJe ?v ?m1) (NakladJe ?n ?m1) (ExCesta ?m1 ?m2))
    :effect (and (VozidloJe ?v ?m2) (NakladJe ?n ?m2) 
                 (not (VozidloJe ?v ?m1))
                 (not (NakladJe ?n ?m1)))
  ) 

  (:action PRENES
    :parameters (?l - letadlo ?n - naklad ?m1 ?m2 - mesto)
    :precondition (and (LetadloJe ?l ?m1) (NakladJe ?n ?m1) (ExSpoj ?m1 ?m2))
    :effect (and (LetadloJe ?l ?m2) (NakladJe ?n ?m2) 
                 (not (LetadloJe ?l ?m1))
                 (not (NakladJe ?n ?m1)))
  ) 

)