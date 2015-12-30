;; A very simple classical STRIPS domain
;; by Radek Marik, 2011.04.18

(define (domain SIMPLE)
  (:requirements :strips :typing)
  (:types typeX)
  (:predicates
    (predA ?x - typeX)
    (predB ?y - typeX)
    (predC)
    (predD)
  )
  
  (:action actionK
    :parameters ()
    :precondition (predC)
    :effect (and (predD))
  )
  
  (:action actionL
    :parameters (?x ?y - typeX)
    :precondition (predA ?x)
    :effect (and (not (predA ?x)) (predB ?y))
  )
 
  (:action actionM
    :parameters (?x - typeX)
    :precondition (predD)
    :effect (and (not (predD)) (predA ?x))
  )
)