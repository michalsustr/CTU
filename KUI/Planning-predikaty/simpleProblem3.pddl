;; A very simple classical STRIPS problem
;; by Radek Marik, 2011.04.18

(define (problem SIMPLE1)
  (:domain SIMPLE)
  (:objects tt uu - typeX)
  (:init (predC))
  (:goal (predB uu))
)